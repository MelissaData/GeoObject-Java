# Melissa GeoCoder Object Windows Java

## Purpose

This code showcases the Melissa GeoCoder Object using Java.

Please feel free to copy or embed this code to your own project. Happy coding!

For the latest Melissa GeoCoder Object release notes, please visit: https://releasenotes.melissa.com/on-premise-api/geocoder-object/

The console will ask the user for:

- Zip 

And return 

For US:

- Place Name
- County
- County Subdivision Name
- Time Zone
- Latitude
- Longitude
- Result Codes

For Canada:

- Time Zone
- Latitude
- Longitude

## Tested Environments

- Windows 10 64-bit Java 19, Powershell 5.1
- Melissa data files for 2024-01

## Required Files and Programs

#### mdGeo.dll

This is the c++ code of the Melissa Object. This file will be downloaded by Melissa Updater!

#### mdGeoJavaWrapper.dll

This file needs to be added as a Project Dependency.  This wrapper will need to be in the same directory as the program using it.

#### Data Files
- mdGeoCode.db3
 
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
This project is compatible with Java 19

#### Install Java
Before starting, make sure that Java has been correctly installed on your machine and your environment paths are configured. 

You can download Java here: 
https://www.oracle.com/java/technologies/downloads/

You can check that your environment is set up correctly by opening a command prompt window and typing the following:
`java --version`

![alt text](/screenshots/java_version.PNG)

If you see the version number then you have installed Java and set up your environment paths correctly!

#### Set up Powershell settings
If running Powershell for the first time, you will need to run this command in the Powershell console: `Set-ExecutionPolicy RemoteSigned`.
The console will then prompt you with the following warning shown in the image below. 
 - Enter `'A'`. 
 	- If successful, the console will not output any messages. (You may need to run Powershell as administrator to enforce this setting).
	
 ![alt text](/screenshots/powershell_executionpolicy.png)

----------------------------------------

#### Download this project
```
$ git clone https://github.com/MelissaData/GeoObject-Java
$ cd GeoObject-Java
```

#### Set up Melissa Updater 
Melissa Updater is a CLI application allowing the user to update their Melissa applications/data. 

- Download Melissa Updater here: <https://releases.melissadata.net/Download/Library/WINDOWS/NET/ANY/latest/MelissaUpdater.exe>
- Create a folder within the cloned repo called `MelissaUpdater`.
- Put `MelissaUpdater.exe` in `MelissaUpdater` folder you just created.

----------------------------------------

#### Different ways to get data file(s)
1.  Using Melissa Updater
	- It will handle all of the data download/path and dll(s) for you. 
2.  If you already have the latest Release (ZIP), you can find the data file(s) and dll(s) in there
	- Use the location of where you copied/installed the data and update the "$DataPath" variable in the powershell script.
	- Copy all the dll(s) mentioned above into the `MelissaGeoCoderObjectWindowsJava` project folder.
	
## Run Powershell Script
Parameters:
- -zip: a test zip code

   - For US: Zip code format can be 5-digit or 9-digit, with or without hyphen(-) delimiter.

   - For Canada: Zip code format must be a full 6-digit Canadian Postal Code, with or without a space.

  This is convenient when you want to get results for a specific zip code in one run instead of testing multiple zip codes in interactive mode.


- -license (optional): a license string to test the GeoCoder Object
- -quiet (optional): add to the command if you do not want to get any console output from the Melissa Updater

When you have modified the script to match your data location, let's run the script.
There are two modes:
- Interactive

	The script will prompt the user for a zip code, then use the provided zip to test GeoCoder Object. For example:
	```
	$ .\MelissaGeoCoderObjectWindowsJava.ps1
	```
	For quiet mode:
	```
	$ .\MelissaGeoCoderObjectWindowsJava.ps1 -quiet
	```
- Command Line

    You can pass a zip code in the ```-zip``` parameter and a license string in ```-license``` parameter to test GeoCoder Object. For example:
    ```
    $ .\MelissaGeoCoderObjectWindowsJava.ps1 -zip "92688"
    $ .\MelissaGeoCoderObjectWindowsJava.ps1 -zip "92688" -license "<your_license_string>"
    ```
    For quiet mode:
    ```
    $ .\MelissaGeoCoderObjectWindowsJava.ps1 -zip "92688" -quiet
    $ .\MelissaGeoCoderObjectWindowsJava.ps1 -zip "92688" -license "<your_license_string>" -quiet
    ```
This is the expected output from a successful setup for interactive mode:


![alt text](/screenshots/output.png)

## Troubleshooting
Troubleshooting for errors found while running your program.

### Errors:
| Error      | Description |
| ----------- | ----------- |
| ErrorRequiredFileNotFound      | Program is missing a required file. Please check your Data folder and refer to the list of required files above. If you are unable to obtain all required files through the Melissa Updater, please contact technical support below. |
| ErrorDatabaseExpired   | .db file(s) are expired. Please make sure you are downloading and using the latest release version. (If using the Melissa Updater, check powershell script for '$RELEASE_VERSION = {version}'  and change the release version if you are using an out of date release).     |
| ErrorFoundOldFile   | File(s) are out of date. Please make sure you are downloading and using the latest release version. (If using the Melissa Updater, check powershell script for '$RELEASE_VERSION = {version}'  and change the release version if you are using an out of date release).    |
| ErrorLicenseExpired   | Expired license string. Please contact technical support below. |


## Contact Us
For free technical support, please call us at 800-MELISSA ext. 4
(800-635-4772 ext. 4) or email us at tech@Melissa.com.

To purchase this product, contact Melissa sales department at
800-MELISSA ext. 3 (800-635-4772 ext. 3).
