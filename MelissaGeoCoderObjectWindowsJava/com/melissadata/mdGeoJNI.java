package com.melissadata;

public class mdGeoJNI {
	static {
		try {
			System.loadLibrary("mdGeoJavaWrapper");
		} catch (UnsatisfiedLinkError ule) {
			System.out.println(ule);
			System.out.println("java.library.path="+System.getProperty("java.library.path"));
		}
	}

	public final static native long mdGeoCreate();
	public final static native void mdGeoDestroy(long I);
	public final static native void SetPathToGeoCodeDataFiles(long I,String p1);
	public final static native void SetPathToGeoPointDataFiles(long I,String p1);
	public final static native void SetPathToGeoCanadaDataFiles(long I,String p1);
	public final static native boolean SetLicenseString(long I,String License);
	public final static native int Initialize(long I,String DataPath,String IndexPath);
	public final static native int InitializeDataFiles(long I);
	public final static native String GetInitializeErrorString(long I);
	public final static native String GetBuildNumber(long I);
	public final static native String GetDatabaseDate(long I);
	public final static native String GetExpirationDate(long I);
	public final static native String GetLicenseExpirationDate(long I);
	public final static native void SetLatitude(long I,String latitude);
	public final static native void SetLongitude(long I,String longitude);
	public final static native boolean WriteToLogFile(long I,String logFile);
	public final static native int GeoCode(long I,String Zip,String Plus4);
	public final static native int GeoPoint(long I,String Zip,String Plus4,String DeliveryPointCode);
	public final static native double ComputeDistance(long I,double Latitude1,double Longitude1,double Latitude2,double Longitude2);
	public final static native double ComputeBearing(long I,double Latitude1,double Longitude1,double Latitude2,double Longitude2);
	public final static native String GetErrorCode(long I);
	public final static native String GetStatusCode(long I);
	public final static native String GetResults(long I);
	public final static native String GetResultCodeDescription(long I,String resultCode,int opt);
	public final static native String GetLatitude(long I);
	public final static native String GetLongitude(long I);
	public final static native String GetCensusTract(long I);
	public final static native String GetCensusBlock(long I);
	public final static native String GetCountyFips(long I);
	public final static native String GetCountyName(long I);
	public final static native String GetPlaceCode(long I);
	public final static native String GetPlaceName(long I);
	public final static native String GetTimeZoneCode(long I);
	public final static native String GetTimeZone(long I);
	public final static native String GetCBSACode(long I);
	public final static native String GetCBSATitle(long I);
	public final static native String GetCBSALevel(long I);
	public final static native String GetCBSADivisionCode(long I);
	public final static native String GetCBSADivisionTitle(long I);
	public final static native String GetCBSADivisionLevel(long I);
	public final static native String GetLastUsageLogMessage(long I);
	public final static native String GetCensusKey(long I);
	public final static native String GetCountySubdivisionCode(long I);
	public final static native String GetCountySubdivisionName(long I);
	public final static native String GetElementarySchoolDistrictCode(long I);
	public final static native String GetElementarySchoolDistrictName(long I);
	public final static native String GetSecondarySchoolDistrictCode(long I);
	public final static native String GetSecondarySchoolDistrictName(long I);
	public final static native String GetStateDistrictLower(long I);
	public final static native String GetStateDistrictUpper(long I);
	public final static native String GetUnifiedSchoolDistrictCode(long I);
	public final static native String GetUnifiedSchoolDistrictName(long I);
	public final static native String GetBlockSuffix(long I);
	public final static native boolean SetInputParameter(long I,String key,String val);
	public final static native void FindGeo(long I);
	public final static native String GetOutputParameter(long I,String key);
}
