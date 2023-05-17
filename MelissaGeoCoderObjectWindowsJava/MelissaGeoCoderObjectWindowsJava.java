import com.melissadata.*;
import java.io.*;

public class MelissaGeoCoderObjectWindowsJava {

  public static void main(String args[]) throws IOException {
    // Variables
    String[] arguments = ParseArguments(args);
    String license = arguments[0];
    String testZip = arguments[1];
    String dataPath = arguments[2];

    RunAsConsole(license, testZip, dataPath);
  }

  public static String[] ParseArguments(String[] args) {
    String license = "", testZip = "", dataPath = "";
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--license") || args[i].equals("-l")) {
        if (args[i + 1] != null) {
          license = args[i + 1];
        }
      }
      if (args[i].equals("--zip") || args[i].equals("-z")) {
        if (args[i + 1] != null) {
          testZip = args[i + 1];
        }
      }
      if (args[i].equals("--dataPath") || args[i].equals("-d")) {
        if (args[i + 1] != null) {
          dataPath = args[i + 1];
        }
      }
    }
    return new String[] { license, testZip, dataPath };

  }

  public static void RunAsConsole(String license, String testZip, String dataPath) throws IOException {
    System.out.println("\n\n========== WELCOME TO MELISSA GEOCODER OBJECT WINDOWS JAVA =========\n");
    GeoObject geoObject = new GeoObject(license, dataPath);
    Boolean shouldContinueRunning = true;

    if (!geoObject.mdGeoObj.GetInitializeErrorString().equals("No error"))
      shouldContinueRunning = false;

    while (shouldContinueRunning) {
      DataContainer dataContainer = new DataContainer();
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

      if (testZip == null || testZip.trim().isEmpty()) {
        System.out.println("\nFill in each value to see the GeoCoder Object results");
        System.out.print("Zip: ");

        dataContainer.Zip = stdin.readLine();
      } else {
        dataContainer.Zip = testZip;
      }

      // Print user input
      System.out.println("\n============================== INPUTS ==============================\n");
      System.out.println("\t               Zip: " + dataContainer.Zip);

      // Execute GeoCoder Object
      geoObject.ExecuteObjectAndResultCodes(dataContainer);

      // Print output
      System.out.println("\n============================== OUTPUT ==============================\n");
      System.out.println("\n\tGeoCoder Object Information:");

      System.out.println("\t              PlaceName: " + geoObject.mdGeoObj.GetPlaceName());
      System.out.println("\t                 County: " + geoObject.mdGeoObj.GetCountyName());
      System.out.println("\t  CountySubdivisionName: " + geoObject.mdGeoObj.GetCountySubdivisionName());
      System.out.println("\t               TimeZone: " + geoObject.mdGeoObj.GetTimeZone());
      System.out.println("\t               Latitude: " + geoObject.mdGeoObj.GetLatitude());
      System.out.println("\t              Longitude: " + geoObject.mdGeoObj.GetLongitude());

      System.out.println("\t  Result Codes: " + dataContainer.ResultCodes);

      String[] rs = dataContainer.ResultCodes.split(",");
      for (String r : rs) {
        System.out.println("        " + r + ":"
            + geoObject.mdGeoObj.GetResultCodeDescription(r, mdGeo.ResultCdDescOpt.ResultCodeDescriptionLong));
      }

      Boolean isValid = false;
      if (testZip != null && !testZip.trim().isEmpty()) {
        isValid = true;
        shouldContinueRunning = false;
      }

      while (!isValid) {
        System.out.println("\nTest another zip? (Y/N)");
        String testAnotherResponse = stdin.readLine();

        if (testAnotherResponse != null && !testAnotherResponse.trim().isEmpty()) {
          testAnotherResponse = testAnotherResponse.toLowerCase();
          if (testAnotherResponse.equals("y")) {
            isValid = true;
          } else if (testAnotherResponse.equals("n")) {
            isValid = true;
            shouldContinueRunning = false;
          } else {
            System.out.println("Invalid Response, please respond 'Y' or 'N'");
          }
        }
      }
    }
    System.out.println("\n=============== THANK YOU FOR USING MELISSA JAVA OBJECT ============\n");

  }
}

class GeoObject {
  // Path to GeoCoder Object data files (.dat, etc)
  String dataFilePath;

  // Create instance of Melissa GeoCoder Object
  mdGeo mdGeoObj = new mdGeo();

  public GeoObject(String license, String dataPath) {
    // Set license string and set path to data files (.dat, etc)
    mdGeoObj.SetLicenseString(license);
    dataFilePath = dataPath;

    // Set data paths for objects
    mdGeoObj.SetPathToGeoCodeDataFiles(dataFilePath);
    mdGeoObj.SetPathToGeoCanadaDataFiles(dataFilePath);
    mdGeoObj.SetPathToGeoPointDataFiles(dataFilePath);

    // If you see a different date than expected, check your license string and
    // either download the new data files or use the Melissa Updater program to
    // update your data files.
    mdGeo.ProgramStatus pStatus = mdGeoObj.InitializeDataFiles();

    if (pStatus != mdGeo.ProgramStatus.ErrorNone) {
      // Problem during initialization
      System.out.println("Failed to Initialize Object.");
      System.out.println(pStatus);
      return;
    }

    System.out.println("                DataBase Date: " + mdGeoObj.GetDatabaseDate());
    System.out.println("              Expiration Date: " + mdGeoObj.GetLicenseExpirationDate());

    /**
     * This number should match with the file properties of the Melissa Object
     * binary file.
     * If TEST appears with the build number, there may be a license key issue.
     */
    System.out.println("               Object Version: " + mdGeoObj.GetBuildNumber());
    System.out.println();

  }

  // This will call the lookup function to process the input zip as well as
  // generate the result codes
  public void ExecuteObjectAndResultCodes(DataContainer data) {

    mdGeoObj.SetInputParameter("Zip", data.Zip);

    mdGeoObj.FindGeo();
    data.ResultCodes = mdGeoObj.GetResults();

    // ResultsCodes explain any issues GeoCoder Object has with the object.
    // List of result codes for GeoCoder Object
    // https://wiki.melissadata.com/?title=Result_Code_Details#GeoCoder_Object

  }
}

class DataContainer {
  public String Zip;
  public String ResultCodes;
}
