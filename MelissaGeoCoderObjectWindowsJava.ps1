# Name:    MelissaGeoCoderObjectWindowsJava
# Purpose: Use the Melissa Updater to make the MelissaGeoCoderObjectWindowsJava code usable


######################### Parameters ##########################

param($zip = '', $license = '', [switch]$quiet = $false )

######################### Classes ##########################

class DLLConfig {
  [string] $FileName;
  [string] $ReleaseVersion;
  [string] $OS;
  [string] $Compiler;
  [string] $Architecture;
  [string] $Type;
}

######################### Config ###########################

$RELEASE_VERSION = '2023.04'
$ProductName = "GEOCODER_DATA"

# Uses the location of the .ps1 file 
# Modify this if you want to use 
$CurrentPath = $PSScriptRoot
Set-Location $CurrentPath
$ProjectPath = "$CurrentPath\MelissaGeoCoderObjectWindowsJava"
$DataPath = "$ProjectPath\Data"
#$BuildPath = "$ProjectPath\Build"

If (!(Test-Path $DataPath)) {
  New-Item -Path $ProjectPath -Name 'Data' -ItemType "directory"
}

# If (!(Test-Path $BuildPath)) {
#   New-Item -Path $ProjectPath -Name 'Build' -ItemType "directory"
# }


$DLLs = @(
  [DLLConfig]@{
    FileName       = "mdGeo.dll";
    ReleaseVersion = $RELEASE_VERSION;
    OS             = "WINDOWS";
    Compiler       = "DLL";
    Architecture   = "64BIT";
    Type           = "BINARY";
  }
)

######################## Functions #########################

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
  
function DownloadDLLs() {
  Write-Host "MELISSA UPDATER IS DOWNLOADING DLL(s)..."
  $DLLProg = 0
  foreach ($DLL in $DLLs) {
    Write-Progress -Activity "Downloading DLL(s)" -Status "$([math]::round($DLLProg / $DLLs.Count * 100, 2))% Complete:"  -PercentComplete ($DLLProg / $DLLs.Count * 100)
  
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
  $License = $env:MD_LICENSE # Get-ChildItem -Path Env:\MD_LICENSE   #[System.Environment]::GetEnvironmentVariable('MD_LICENSE')
}
  
if ([string]::IsNullOrEmpty($License)) {
  Write-Host "`nLicense String is invalid!"
  Exit
}
# Use Melissa Updater to download data file(s) 
# Download data file(s) 
DownloadDataFiles -license $License      # comment out this line if using DQS Release
  
# Set data file(s) path
#$DataPath = "C:\Program Files\Melissa DATA\DQT\Data"      # uncomment this line and change to your DQS Release data file(s) directory 
  
# Download dll(s)
DownloadDlls -license $License
  
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
if ([string]::IsNullOrEmpty($zip)) {
  java -jar MelissaGeoCoderObjectWindowsJava.jar --license $License --dataPath $DataPath
}
else {
  java -jar MelissaGeoCoderObjectWindowsJava.jar --license $License --dataPath $DataPath --zip $zip
}
Set-Location ..