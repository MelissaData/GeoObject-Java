import com.melissadata.*;
import java.io.*;

/**
 * GeoCoder Object enables you to access geographic data using your ZIP Code and optional
 * Plus4. This allows you to obtain latitude and longitude geographic coordinates, census
 * tract and block numbers, as well as county name and FIPS numbers.
 *
 * <p>High-level flow of this sample:
 * <ol>
 *   <li>SETUP     - create an mdGeo instance, hand it the license string and the
 *                   paths to the data files, then InitializeDataFiles() (one time).</li>
 *   <li>INPUT     - supply a ZIP via SetInputParameter("Zip", ...).</li>
 *   <li>PROCESS   - FindGeo() looks up the geographic data for that ZIP.</li>
 *   <li>READ      - pull the results back out with the Get* getters
 *                   (GetLatitude, GetLongitude, GetCountyName, GetPlaceName, ...).</li>
 *   <li>INTERPRET - GetResults() returns comma-separated result codes describing
 *                   what the object did/found; each code has a human description.</li>
 * </ol>
 *
 * <p>The pieces in this file map onto that flow:
 * <ul>
 *   <li>main / RunAsConsole / ParseArguments : console harness (argument parsing + the interactive loop).</li>
 *   <li>GeoObject                            : thin wrapper around mdGeo that owns setup + the call sequence.</li>
 *   <li>DataContainer                        : plain holder for one record's input and output.</li>
 * </ul>
 *
 * <p>Where mdGeo comes from:
 * The mdGeo and mdGeoJNI classes in com/melissadata come from mdGeo_JavaCode.zip,
 * which the accompanying MelissaGeoCoderObjectWindowsJava.ps1 script downloads and
 * expands into com/melissadata on every run. mdGeoJNI declares the native methods and
 * loads mdGeoJavaWrapper.dll, the JNI shim that calls into mdGeo.dll.
 *
 * <p>Reference:
 * <ul>
 *   <li>Quickstart    : https://docs.melissa.com/on-premise-api/geocoder-object/geocoder-object-quickstart.html</li>
 *   <li>Release notes : https://releasenotes.melissa.com/on-premise-api/geocoder-object/</li>
 *   <li>Result codes  : https://docs.melissa.com/on-premise-api/geocoder-object/result-codes.html</li>
 * </ul>
 */
public class MelissaGeoCoderObjectWindowsJava {

  /**
   * Entry point. Reads the optional command-line arguments, then hands control to
   * RunAsConsole, which performs the actual GeoCoder Object setup and processing.
   *
   * @param args The raw command-line arguments
   * @throws IOException if reading from standard input fails
   */
  public static void main(String args[]) throws IOException {
    // Populated by ParseArguments below.
    String[] arguments = ParseArguments(args);
    String license = arguments[0];
    String testZip = arguments[1];
    String dataPath = arguments[2];

    RunAsConsole(license, testZip, dataPath);
  }

  /**
   * Reads the supported command-line options and returns them.
   *
   * <p>Recognized flags (each followed by its value, e.g. "--zip 92688"):
   * <ul>
   *   <li>--license / -l   : the Melissa license string</li>
   *   <li>--dataPath / -d  : path to the GeoCoder Object data files</li>
   *   <li>--zip / -z       : a ZIP code to test in one-shot mode</li>
   * </ul>
   *
   * @param args The raw command-line arguments to parse.
   * @return A String array of { license, testZip, dataPath }.
   */
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

  /**
   * Sets up the GeoCoder Object once, then drives the input -> process -> output cycle.
   *
   * <p>In interactive mode (no --zip) it loops, asking for a new ZIP each pass until the
   * user answers "N". In one-shot mode (--zip supplied) it runs a single pass
   * and exits.
   *
   * @param license  The Melissa license string used to initialize the object.
   * @param testZip  A ZIP code to process in one-shot mode; if empty, the program prompts interactively.
   * @param dataPath Path to the GeoCoder Object data files.
   * @throws IOException if reading from standard input fails
   */
  public static void RunAsConsole(String license, String testZip, String dataPath) throws IOException {
    System.out.println("\n\n========== WELCOME TO MELISSA GEOCODER OBJECT WINDOWS JAVA =========\n");

    // Construct the wrapper. This is where the object is licensed, pointed at the
    // data files, and initialized (see the GeoObject constructor below).
    GeoObject geoObject = new GeoObject(license, dataPath);
    Boolean shouldContinueRunning = true;

    // Gate the program on a successful initialization. If the data files could not
    // be loaded (bad/expired license, missing or wrong-path data files, ...),
    // GetInitializeErrorString() returns the reason instead of "No error" and we
    // skip the processing loop entirely.
    if (!geoObject.mdGeoObj.GetInitializeErrorString().equals("No error"))
      shouldContinueRunning = false;

    while (shouldContinueRunning) {
      // Holder for this pass's input and result codes.
      DataContainer dataContainer = new DataContainer();
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

      if (testZip == null || testZip.trim().isEmpty()) {
        // Interactive mode: prompt the user for a ZIP code.
        System.out.println("\nFill in each value to see the GeoCoder Object results");
        System.out.print("Zip: ");

        dataContainer.Zip = stdin.readLine();
      } else {
        // One-shot mode: use the ZIP passed on the command line.
        dataContainer.Zip = testZip;
      }

      // Print user input
      System.out.println("\n============================== INPUTS ==============================\n");
      System.out.println("\t               Zip: " + dataContainer.Zip);

      // Execute GeoCoder Object
      // Runs the FindGeo lookup and stores the result codes on dataContainer.
      geoObject.ExecuteObjectAndResultCodes(dataContainer);

      // Print output
      // Each Get* getter below returns one component the object produced for the most
      // recently processed ZIP. These read directly from the mdGeo instance, which
      // still holds the results from the Execute call above.
      System.out.println("\n============================== OUTPUT ==============================\n");
      System.out.println("\n\tGeoCoder Object Information:");

      System.out.println("\t              PlaceName: " + geoObject.mdGeoObj.GetPlaceName());
      System.out.println("\t                 County: " + geoObject.mdGeoObj.GetCountyName());
      System.out.println("\t  CountySubdivisionName: " + geoObject.mdGeoObj.GetCountySubdivisionName());
      System.out.println("\t               TimeZone: " + geoObject.mdGeoObj.GetTimeZone());
      System.out.println("\t               Latitude: " + geoObject.mdGeoObj.GetLatitude());
      System.out.println("\t              Longitude: " + geoObject.mdGeoObj.GetLongitude());

      System.out.println("\t  Result Codes: " + dataContainer.ResultCodes);

      // Result codes come back as a single comma-separated string (e.g. "GS05,GS06").
      // Split it and ask the object for a readable description of each code.
      // ResultCodeDescriptionLong requests the long-form text; a short form is also
      // available via ResultCodeDescriptionShort
      String[] rs = dataContainer.ResultCodes.split(",");
      for (String r : rs) {
        System.out.println("        " + r + ":"
            + geoObject.mdGeoObj.GetResultCodeDescription(r, mdGeo.ResultCdDescOpt.ResultCodeDescriptionLong));
      }

      Boolean isValid = false;

      // In one-shot mode there is nothing more to do after a single pass: mark the
      // input handled and stop the outer loop.
      if (testZip != null && !testZip.trim().isEmpty()) {
        isValid = true;
        shouldContinueRunning = false;
      }

      // Interactive mode: ask whether to process another ZIP. Keep prompting until
      // we get a valid Y/N. "N" ends the program; "Y" falls through to another pass.
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

/**
 * Wrapper that owns a single Melissa GeoCoder Object instance and encapsulates the two
 * things every Melissa object needs: one-time setup (license + data files) and the
 * per-record processing sequence. Reuse one instance across many lookups; do NOT
 * re-initialize per lookup.
 */
class GeoObject {
  // Path to the GeoCoder Object data files.
  String dataFilePath;

  // The underlying Melissa GeoCoder Object instance.
  mdGeo mdGeoObj = new mdGeo();

  /**
   * Performs the mandatory one-time setup, in this required order:
   * <ol>
   *   <li>SetLicenseString     - authorize the object.</li>
   *   <li>SetPathTo*DataFiles  - tell it where each set of data files lives.</li>
   *   <li>InitializeDataFiles  - load the data into memory.</li>
   * </ol>
   *
   * @param license  The Melissa license string used to authorize the object.
   * @param dataPath Path to the folder containing the GeoCoder Object data files.
   */
  public GeoObject(String license, String dataPath) {
    // Set license string and set path to data files
    mdGeoObj.SetLicenseString(license);
    dataFilePath = dataPath;

    // Point the object at the GeoCoder data files (US GeoCode, Canada, and GeoPoint).
    mdGeoObj.SetPathToGeoCodeDataFiles(dataFilePath);
    mdGeoObj.SetPathToGeoCanadaDataFiles(dataFilePath);
    mdGeoObj.SetPathToGeoPointDataFiles(dataFilePath);

    // Load the data files. The returned ProgramStatus reports whether initialization succeeded.
    // If you see a different date than expected, check your license string and either download the new data files
    // or use the Melissa Updater program to update your data files.
    mdGeo.ProgramStatus pStatus = mdGeoObj.InitializeDataFiles();

    // If an issue occurred, please investigate the common causes.
    // Common causes: an invalid/expired license, or missing/wrong-path data files.
    if (pStatus != mdGeo.ProgramStatus.ErrorNone) {
      System.out.println("Failed to Initialize Object.");
      System.out.println(pStatus);
      return;
    }

    // Diagnostic information, handy for confirming the object loaded the data you expect:

    // Build date of the data files
    System.out.println("                DataBase Date: " + mdGeoObj.GetDatabaseDate());

    // When the license stops working
    System.out.println("              Expiration Date: " + mdGeoObj.GetLicenseExpirationDate());

    // This number should match with the file properties of the Melissa Object binary file.
    // If TEST appears with the build number, there may be a license key issue.
    System.out.println("               Object Version: " + mdGeoObj.GetBuildNumber());
    System.out.println();

  }

  /**
   * Runs the full GeoCoder Object processing sequence for one ZIP and captures its
   * result codes. This is the canonical per-record call pattern to copy into your
   * own application:
   *   SetInputParameter -> FindGeo -> GetResults
   *
   * @param data The record to process. Its Zip is read as input, and ResultCodes is
   *             populated with this run's result codes.
   */
  public void ExecuteObjectAndResultCodes(DataContainer data) {

    // Supply the ZIP to look up
    mdGeoObj.SetInputParameter("Zip", data.Zip);

    // Look up the geographic data for that ZIP
    mdGeoObj.FindGeo();

    // Collect the result codes for this run
    // ResultsCodes explain any issues GeoCoder Object has with the object.
    // List of result codes for GeoCoder Object
    // https://docs.melissa.com/on-premise-api/geocoder-object/result-codes.html
    data.ResultCodes = mdGeoObj.GetResults();
  }
}

/**
 * Data holder for a single record: carries the input ZIP in and the result codes out.
 */
class DataContainer {
  // Input: the ZIP code to look up.
  public String Zip;

  // Output: comma-separated result codes from GetResults().
  public String ResultCodes;
}
