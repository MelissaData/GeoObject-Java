package com.melissadata;

public class mdGeo {
	private long I;
	protected boolean ownMemory;

	protected static long getI(mdGeo obj) {
		return (obj==null ? 0 : obj.I);
	}

	protected void finalize() {
		delete();
	}

public final static class ProgramStatus {
	public final static mdGeo.ProgramStatus ErrorNone=new mdGeo.ProgramStatus("ErrorNone",0);
	public final static mdGeo.ProgramStatus ErrorOther=new mdGeo.ProgramStatus("ErrorOther",1);
	public final static mdGeo.ProgramStatus ErrorOutOfMemory=new mdGeo.ProgramStatus("ErrorOutOfMemory",2);
	public final static mdGeo.ProgramStatus ErrorRequiredFileNotFound=new mdGeo.ProgramStatus("ErrorRequiredFileNotFound",3);
	public final static mdGeo.ProgramStatus ErrorFoundOldFile=new mdGeo.ProgramStatus("ErrorFoundOldFile",4);
	public final static mdGeo.ProgramStatus ErrorDatabaseExpired=new mdGeo.ProgramStatus("ErrorDatabaseExpired",5);
	public final static mdGeo.ProgramStatus ErrorLicenseExpired=new mdGeo.ProgramStatus("ErrorLicenseExpired",6);

	private final String enumName;
	private final int enumValue;
	private static ProgramStatus[] enumValues={ErrorNone,ErrorOther,ErrorOutOfMemory,ErrorRequiredFileNotFound,ErrorFoundOldFile,ErrorDatabaseExpired,ErrorLicenseExpired};

	private ProgramStatus(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static ProgramStatus toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+ProgramStatus.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class AccessType {
	public final static mdGeo.AccessType Local=new mdGeo.AccessType("Local",0);
	public final static mdGeo.AccessType Remote=new mdGeo.AccessType("Remote",1);

	private final String enumName;
	private final int enumValue;
	private static AccessType[] enumValues={Local,Remote};

	private AccessType(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static AccessType toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+AccessType.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class DiacriticsMode {
	public final static mdGeo.DiacriticsMode Auto=new mdGeo.DiacriticsMode("Auto",0);
	public final static mdGeo.DiacriticsMode On=new mdGeo.DiacriticsMode("On",1);
	public final static mdGeo.DiacriticsMode Off=new mdGeo.DiacriticsMode("Off",2);

	private final String enumName;
	private final int enumValue;
	private static DiacriticsMode[] enumValues={Auto,On,Off};

	private DiacriticsMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static DiacriticsMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+DiacriticsMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class StandardizeMode {
	public final static mdGeo.StandardizeMode ShortFormat=new mdGeo.StandardizeMode("ShortFormat",0);
	public final static mdGeo.StandardizeMode LongFormat=new mdGeo.StandardizeMode("LongFormat",1);
	public final static mdGeo.StandardizeMode AutoFormat=new mdGeo.StandardizeMode("AutoFormat",2);

	private final String enumName;
	private final int enumValue;
	private static StandardizeMode[] enumValues={ShortFormat,LongFormat,AutoFormat};

	private StandardizeMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static StandardizeMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+StandardizeMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class SuiteParseMode {
	public final static mdGeo.SuiteParseMode ParseSuite=new mdGeo.SuiteParseMode("ParseSuite",0);
	public final static mdGeo.SuiteParseMode CombineSuite=new mdGeo.SuiteParseMode("CombineSuite",1);

	private final String enumName;
	private final int enumValue;
	private static SuiteParseMode[] enumValues={ParseSuite,CombineSuite};

	private SuiteParseMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static SuiteParseMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+SuiteParseMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class AliasPreserveMode {
	public final static mdGeo.AliasPreserveMode ConvertAlias=new mdGeo.AliasPreserveMode("ConvertAlias",0);
	public final static mdGeo.AliasPreserveMode PreserveAlias=new mdGeo.AliasPreserveMode("PreserveAlias",1);

	private final String enumName;
	private final int enumValue;
	private static AliasPreserveMode[] enumValues={ConvertAlias,PreserveAlias};

	private AliasPreserveMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static AliasPreserveMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+AliasPreserveMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class AutoCompletionMode {
	public final static mdGeo.AutoCompletionMode AutoCompleteSingleSuite=new mdGeo.AutoCompletionMode("AutoCompleteSingleSuite",0);
	public final static mdGeo.AutoCompletionMode AutoCompleteRangedSuite=new mdGeo.AutoCompletionMode("AutoCompleteRangedSuite",1);
	public final static mdGeo.AutoCompletionMode AutoCompletePlaceHolderSuite=new mdGeo.AutoCompletionMode("AutoCompletePlaceHolderSuite",2);
	public final static mdGeo.AutoCompletionMode AutoCompleteNoSuite=new mdGeo.AutoCompletionMode("AutoCompleteNoSuite",3);

	private final String enumName;
	private final int enumValue;
	private static AutoCompletionMode[] enumValues={AutoCompleteSingleSuite,AutoCompleteRangedSuite,AutoCompletePlaceHolderSuite,AutoCompleteNoSuite};

	private AutoCompletionMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static AutoCompletionMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+AutoCompletionMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class ResultCdDescOpt {
	public final static mdGeo.ResultCdDescOpt ResultCodeDescriptionLong=new mdGeo.ResultCdDescOpt("ResultCodeDescriptionLong",0);
	public final static mdGeo.ResultCdDescOpt ResultCodeDescriptionShort=new mdGeo.ResultCdDescOpt("ResultCodeDescriptionShort",1);

	private final String enumName;
	private final int enumValue;
	private static ResultCdDescOpt[] enumValues={ResultCodeDescriptionLong,ResultCodeDescriptionShort};

	private ResultCdDescOpt(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static ResultCdDescOpt toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+ResultCdDescOpt.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class MailboxLookupMode {
	public final static mdGeo.MailboxLookupMode MailboxNone=new mdGeo.MailboxLookupMode("MailboxNone",0);
	public final static mdGeo.MailboxLookupMode MailboxExpress=new mdGeo.MailboxLookupMode("MailboxExpress",1);
	public final static mdGeo.MailboxLookupMode MailboxPremium=new mdGeo.MailboxLookupMode("MailboxPremium",2);

	private final String enumName;
	private final int enumValue;
	private static MailboxLookupMode[] enumValues={MailboxNone,MailboxExpress,MailboxPremium};

	private MailboxLookupMode(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static MailboxLookupMode toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+MailboxLookupMode.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

	protected mdGeo(long i,boolean own) {
		ownMemory=own;
		I=i;
	}

	public mdGeo() {
		this(mdGeoJNI.mdGeoCreate(),true);
	}

	public synchronized void delete() {
		if (I!=0) {
			if (ownMemory) {
				ownMemory=false;
				mdGeoJNI.mdGeoDestroy(I);
			}
			I=0;
		}
	}

	public void SetPathToGeoCodeDataFiles(String p1) {
		mdGeoJNI.SetPathToGeoCodeDataFiles(I,p1);
	}

	public void SetPathToGeoPointDataFiles(String p1) {
		mdGeoJNI.SetPathToGeoPointDataFiles(I,p1);
	}

	public void SetPathToGeoCanadaDataFiles(String p1) {
		mdGeoJNI.SetPathToGeoCanadaDataFiles(I,p1);
	}

	public boolean SetLicenseString(String License) {
		return mdGeoJNI.SetLicenseString(I,License);
	}

	public ProgramStatus Initialize(String DataPath, String IndexPath) {
		return ProgramStatus.toEnum(mdGeoJNI.Initialize(I,DataPath,IndexPath));
	}

	public ProgramStatus InitializeDataFiles() {
		return ProgramStatus.toEnum(mdGeoJNI.InitializeDataFiles(I));
	}

	public String GetInitializeErrorString() {
		return mdGeoJNI.GetInitializeErrorString(I);
	}

	public String GetBuildNumber() {
		return mdGeoJNI.GetBuildNumber(I);
	}

	public String GetDatabaseDate() {
		return mdGeoJNI.GetDatabaseDate(I);
	}

	public String GetExpirationDate() {
		return mdGeoJNI.GetExpirationDate(I);
	}

	public String GetLicenseExpirationDate() {
		return mdGeoJNI.GetLicenseExpirationDate(I);
	}

	public void SetLatitude(String latitude) {
		mdGeoJNI.SetLatitude(I,latitude);
	}

	public void SetLongitude(String longitude) {
		mdGeoJNI.SetLongitude(I,longitude);
	}

	public boolean WriteToLogFile(String logFile) {
		return mdGeoJNI.WriteToLogFile(I,logFile);
	}

	public int GeoCode(String Zip) {
		return mdGeoJNI.GeoCode(I,Zip,"");
	}
	public int GeoCode(String Zip, String Plus4) {
		return mdGeoJNI.GeoCode(I,Zip,Plus4);
	}

	public int GeoPoint(String Zip, String Plus4, String DeliveryPointCode) {
		return mdGeoJNI.GeoPoint(I,Zip,Plus4,DeliveryPointCode);
	}

	public double ComputeDistance(double Latitude1, double Longitude1, double Latitude2, double Longitude2) {
		return mdGeoJNI.ComputeDistance(I,Latitude1,Longitude1,Latitude2,Longitude2);
	}

	public double ComputeBearing(double Latitude1, double Longitude1, double Latitude2, double Longitude2) {
		return mdGeoJNI.ComputeBearing(I,Latitude1,Longitude1,Latitude2,Longitude2);
	}

	public String GetErrorCode() {
		return mdGeoJNI.GetErrorCode(I);
	}

	public String GetStatusCode() {
		return mdGeoJNI.GetStatusCode(I);
	}

	public String GetResults() {
		return mdGeoJNI.GetResults(I);
	}

	public String GetResultCodeDescription(String resultCode) {
		return mdGeoJNI.GetResultCodeDescription(I,resultCode,0);
	}
	public String GetResultCodeDescription(String resultCode, mdGeo.ResultCdDescOpt opt) {
		return mdGeoJNI.GetResultCodeDescription(I,resultCode,opt.toValue());
	}

	public String GetLatitude() {
		return mdGeoJNI.GetLatitude(I);
	}

	public String GetLongitude() {
		return mdGeoJNI.GetLongitude(I);
	}

	public String GetCensusTract() {
		return mdGeoJNI.GetCensusTract(I);
	}

	public String GetCensusBlock() {
		return mdGeoJNI.GetCensusBlock(I);
	}

	public String GetCountyFips() {
		return mdGeoJNI.GetCountyFips(I);
	}

	public String GetCountyName() {
		return mdGeoJNI.GetCountyName(I);
	}

	public String GetPlaceCode() {
		return mdGeoJNI.GetPlaceCode(I);
	}

	public String GetPlaceName() {
		return mdGeoJNI.GetPlaceName(I);
	}

	public String GetTimeZoneCode() {
		return mdGeoJNI.GetTimeZoneCode(I);
	}

	public String GetTimeZone() {
		return mdGeoJNI.GetTimeZone(I);
	}

	public String GetCBSACode() {
		return mdGeoJNI.GetCBSACode(I);
	}

	public String GetCBSATitle() {
		return mdGeoJNI.GetCBSATitle(I);
	}

	public String GetCBSALevel() {
		return mdGeoJNI.GetCBSALevel(I);
	}

	public String GetCBSADivisionCode() {
		return mdGeoJNI.GetCBSADivisionCode(I);
	}

	public String GetCBSADivisionTitle() {
		return mdGeoJNI.GetCBSADivisionTitle(I);
	}

	public String GetCBSADivisionLevel() {
		return mdGeoJNI.GetCBSADivisionLevel(I);
	}

	public String GetLastUsageLogMessage() {
		return mdGeoJNI.GetLastUsageLogMessage(I);
	}

	public String GetCensusKey() {
		return mdGeoJNI.GetCensusKey(I);
	}

	public String GetCountySubdivisionCode() {
		return mdGeoJNI.GetCountySubdivisionCode(I);
	}

	public String GetCountySubdivisionName() {
		return mdGeoJNI.GetCountySubdivisionName(I);
	}

	public String GetElementarySchoolDistrictCode() {
		return mdGeoJNI.GetElementarySchoolDistrictCode(I);
	}

	public String GetElementarySchoolDistrictName() {
		return mdGeoJNI.GetElementarySchoolDistrictName(I);
	}

	public String GetSecondarySchoolDistrictCode() {
		return mdGeoJNI.GetSecondarySchoolDistrictCode(I);
	}

	public String GetSecondarySchoolDistrictName() {
		return mdGeoJNI.GetSecondarySchoolDistrictName(I);
	}

	public String GetStateDistrictLower() {
		return mdGeoJNI.GetStateDistrictLower(I);
	}

	public String GetStateDistrictUpper() {
		return mdGeoJNI.GetStateDistrictUpper(I);
	}

	public String GetUnifiedSchoolDistrictCode() {
		return mdGeoJNI.GetUnifiedSchoolDistrictCode(I);
	}

	public String GetUnifiedSchoolDistrictName() {
		return mdGeoJNI.GetUnifiedSchoolDistrictName(I);
	}

	public String GetBlockSuffix() {
		return mdGeoJNI.GetBlockSuffix(I);
	}

	public boolean SetInputParameter(String key, String val) {
		return mdGeoJNI.SetInputParameter(I,key,val);
	}

	public void FindGeo() {
		mdGeoJNI.FindGeo(I);
	}

	public String GetOutputParameter(String key) {
		return mdGeoJNI.GetOutputParameter(I,key);
	}

}
