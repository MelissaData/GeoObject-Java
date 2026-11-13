<#
.SYNOPSIS
    Downloads the required components and then builds and runs MelissaGeoCoderObjectWindowsJava

.DESCRIPTION
    This script uses the Melissa Updater to fetch the data file(s), the DLL(s), the JNI
    wrapper DLL, and a zip of the Java interface source, expands that zip into
    com\melissadata, verifies the product DLL(s) downloaded, then compiles the sample with javac,
    packages it into a jar, and runs it against the supplied ZIP code.

    Overall flow:
      1. Read parameters / prompt for the license and data path.
      2. Download data file(s), DLL(s), and the Java wrapper via the Melissa Updater,
         expanding the interface source into com\melissadata.
      3. Confirm the product DLL(s) are present (the JNI wrapper DLL is not checked).
      4. Compile, package, and run (single test ZIP code or interactive).

.PARAMETER zip
    ZIP code to look up.

.PARAMETER dataPath
    Path to an existing data files directory. If omitted, the script prompts for
    a path; pressing Enter at that prompt skips it and downloads the data files
    into the project's Data folder via the Melissa Updater. A path that does not
    exist aborts the script.

.PARAMETER license
    License string. Resolved in this order:
      1. This parameter.
      2. An interactive prompt, if the parameter was not supplied.
      3. The MD_LICENSE environment variable, if the prompt was left blank.
    Note that the environment variable is the last resort, not the first: running
    without -license always prompts, even when MD_LICENSE is set.

.PARAMETER quiet
    Suppresses the Melissa Updater console output during the DLL and wrapper
    downloads. The data file download is not affected.

.EXAMPLE
    .\MelissaGeoCoderObjectWindowsJava.ps1 -license "your-license"

.EXAMPLE
    .\MelissaGeoCoderObjectWindowsJava.ps1 -zip "92688" -license "your-license"
#>


######################### Parameters ##########################

param($zip = '', $dataPath = '', $license = '', [switch]$quiet = $false )

######################### Classes ##########################

# Describes a single file to request from the Melissa Updater
class FileConfig {
  [string] $FileName;
  [string] $ReleaseVersion;
  [string] $OS;
  [string] $Compiler;
  [string] $Architecture;
  [string] $Type;
}

######################### Config ###########################

# Product release the updater pulls files for
$RELEASE_VERSION = '2026.Q4'
$ProductName = "GEOCODER_DATA"

# Uses the location of the .ps1 file 
$CurrentPath = $PSScriptRoot
Set-Location $CurrentPath
$ProjectPath = "$CurrentPath\MelissaGeoCoderObjectWindowsJava"

if ([string]::IsNullOrEmpty($dataPath)) {
  $DataPath = "$ProjectPath\Data" 
}

if (!(Test-Path $DataPath) -and ($DataPath -eq "$ProjectPath\Data")) {
  New-Item -Path $ProjectPath -Name 'Data' -ItemType "directory"
}
elseif (!(Test-Path $DataPath) -and ($DataPath -ne "$ProjectPath\Data")) {
  Write-Host "`nData file path does not exist. Please check that your file path is correct."
  Write-Host "`nAborting program, see above.  Press any button to exit.`n"
  $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") > $null
  exit
}

# Binary/DLL(s) needed to run the example
$DLLs = @(
  [FileConfig]@{
    FileName       = "mdGeo.dll";
    ReleaseVersion = $RELEASE_VERSION;
    OS             = "WINDOWS";
    Compiler       = "DLL";
    Architecture   = "64BIT";
    Type           = "BINARY";
  }
)

# The JNI wrapper DLL and the zip of Java interface source that exposes the DLL(s)
# to the sample; the zip is expanded into com\melissadata
$WrapperCom = @(
  [FileConfig]@{
    FileName       = "mdGeoJavaWrapper.dll";
    ReleaseVersion = $RELEASE_VERSION;
    OS             = "WINDOWS";
    Compiler       = "JAVA";
    Architecture   = "64BIT";
    Type           = "INTERFACE";
  },
  [FileConfig]@{
    FileName       = "mdGeo_JavaCode.zip";
    ReleaseVersion = $RELEASE_VERSION;
    OS             = "ANY";
    Compiler       = "JAVA";
    Architecture   = "ANY";
    Type           = "INTERFACE";
  }
)

######################## Functions #########################

# Download the product data file(s) into $DataPath via the Melissa Updater.
function DownloadDataFiles([string] $license) {
  $DataProg = 0
  Write-Host "========================== MELISSA UPDATER ========================="
  Write-Host "MELISSA UPDATER IS DOWNLOADING DATA FILE(S)..."
  
  .\MelissaUpdater\MelissaUpdater.exe manifest -p $ProductName -r $RELEASE_VERSION -l $license -t $DataPath 
  if ($? -eq $False ) {
    Write-Host "`nCannot run Melissa Updater. Please check your license string!"
    Exit
  }     
  Write-Host "Melissa Updater finished downloading data file(s)!"
  
}
  
# Download each DLL in $DLLs into the project folder (with a progress bar).
function DownloadDLLs() {
  Write-Host "MELISSA UPDATER IS DOWNLOADING DLL(S)..."
  $DLLProg = 0
  foreach ($DLL in $DLLs) {
    Write-Progress -Activity "Downloading DLL(S)" -Status "$([math]::round($DLLProg / $DLLs.Count * 100, 2))% Complete:"  -PercentComplete ($DLLProg / $DLLs.Count * 100)
  
    # Check for quiet mode
    if ($quiet) {
      .\MelissaUpdater\MelissaUpdater.exe file --filename $DLL.FileName --release_version $DLL.ReleaseVersion --license $LICENSE --os $DLL.OS --compiler $DLL.Compiler --architecture $DLL.Architecture --type $DLL.Type --target_directory $ProjectPath > $null
      if (($?) -eq $False) {
        Write-Host "`nCannot run Melissa Updater. Please check your license string!"
        Exit
      }
    }
    else {
      .\MelissaUpdater\MelissaUpdater.exe file --filename $DLL.FileName --release_version $DLL.ReleaseVersion --license $LICENSE --os $DLL.OS --compiler $DLL.Compiler --architecture $DLL.Architecture --type $DLL.Type --target_directory $ProjectPath 
      if (($?) -eq $False) {
        Write-Host "`nCannot run Melissa Updater. Please check your license string!"
        Exit
      }
    }
      
    Write-Host "Melissa Updater finished downloading " $DLL.FileName "!"
    $DLLProg++
  }
}

# Download the JNI wrapper DLL and the Java interface zip, then expand the zip
# into com\melissadata (replacing any previous copy). Aborts if the zip is missing
# after the download.
function DownloadWrappers() {
  foreach ($File in $WrapperCom) {
    # Check for quiet mode
    if ($quiet) {
      .\MelissaUpdater\MelissaUpdater.exe file --filename $File.FileName --release_version $File.ReleaseVersion --license $LICENSE --os $File.OS --compiler $File.Compiler --architecture $File.Architecture --type $File.Type --target_directory $ProjectPath > $null
      if (($?) -eq $False) {
        Write-Host "`nCannot run Melissa Updater. Please check your license string!"
        Exit
      }
    }
    else {
      .\MelissaUpdater\MelissaUpdater.exe file --filename $File.FileName --release_version $File.ReleaseVersion --license $LICENSE --os $File.OS --compiler $File.Compiler --architecture $File.Architecture --type $File.Type --target_directory $ProjectPath 
      if (($?) -eq $False) {
        Write-Host "`nCannot run Melissa Updater. Please check your license string!"
        Exit
      }
    }
      
    Write-Host "Melissa Updater finished downloading " $File.FileName "!"

    # Check for the zip folder and extract from the zip folder if it was downloaded
    if ($File.FileName -eq "mdGeo_JavaCode.zip") {
      if (!(Test-Path ("$ProjectPath\mdGeo_JavaCode.zip"))) {
        Write-Host "mdGeo_JavaCode.zip not found." 
        
        Write-Host "`nAborting program, see above.  Press any button to exit."
        $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        exit
      }
      else {
        if (!(Test-Path ("$ProjectPath/com"))) {
        Expand-Archive -Path "$ProjectPath\mdGeo_JavaCode.zip" -DestinationPath $ProjectPath
        }
        else {
          # Remove the com folder before extracting
          Remove-Item -Path "$ProjectPath/com" -Recurse -Force

          Expand-Archive -Path "$ProjectPath\mdGeo_JavaCode.zip" -DestinationPath $ProjectPath
        }
      }
    }
  }
}

# Verify the expected DLL(s) landed in the project folder
function CheckDLLs() {
  Write-Host "`nDouble checking dll(s) were downloaded...`n"
  $FileMissing = $false 
  if (!(Test-Path ("$ProjectPath\mdGeo.dll"))) {
    Write-Host "mdGeo.dll not found." 
    $FileMissing = $true
  }
  if ($FileMissing) {
    Write-Host "`nMissing the above data file(s).  Please check that your license string and directory are correct."
    return $false
  }
  else {
    return $true
  }
}
  
########################## Main ############################
  
Write-Host "`n===================== Melissa GeoCoder Object ======================`n                    [ Java | Windows | 64BIT ]`n"
  
# Get license (either from parameters or user input)
if ([string]::IsNullOrEmpty($license) ) {
  $License = Read-Host "Please enter your license string"
}
  
# Check for License from Environment Variables 
if ([string]::IsNullOrEmpty($License) ) {
  $License = $env:MD_LICENSE 
}
  
if ([string]::IsNullOrEmpty($License)) {
  Write-Host "`nLicense String is invalid!"
  Exit
}

# Get data file path (either from parameters or user input)
if ($DataPath -eq "$ProjectPath\Data") {
  $dataPathInput = Read-Host "Please enter your data files path directory if you have already downloaded the release zip.`nOtherwise, the data files will be downloaded using the Melissa Updater (Enter to skip)"

  if (![string]::IsNullOrEmpty($dataPathInput)) {
    if (!(Test-Path $dataPathInput)) {
      Write-Host "`nData file path does not exist. Please check that your file path is correct."
      Write-Host "`nAborting program, see above.  Press any button to exit.`n"
      $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") > $null
      exit
    }
    else {
      $DataPath = $dataPathInput
    }
  }
}

# Use Melissa Updater to download data file(s) 
# Download data file(s) 
DownloadDataFiles -license $License # Comment out this line if using own release
  
# Download dll(s)
DownloadDlls -license $License

# Download wrapper and com folder
DownloadWrappers -license $License

# Check if all dll(s) have been downloaded. Exit script if missing
$DLLsAreDownloaded = CheckDLLs
  
if (!$DLLsAreDownloaded) {
  Write-Host "`nAborting program, see above.  Press any button to exit."
  $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
  exit
}
  
Write-Host "All file(s) have been downloaded/updated! "
  
  
# Start
# Build project
Set-Location $ProjectPath
Write-Host "`n=========================== BUILD PROJECT =========================="
javac MelissaGeoCoderObjectWindowsJava.java
jar cvfm MelissaGeoCoderObjectWindowsJava.jar manifest.txt *.class *.dll com\melissadata\*.class

# Run project
# No ZIP code supplied -> run interactively; otherwise pass the ZIP code in.
# The build step above switched into the project folder; Set-Location .. returns afterwards.
if ([string]::IsNullOrEmpty($zip)) {
  java -jar MelissaGeoCoderObjectWindowsJava.jar --license $License --dataPath $DataPath
}
else {
  java -jar MelissaGeoCoderObjectWindowsJava.jar --license $License --dataPath $DataPath --zip $zip
}
Set-Location ..
