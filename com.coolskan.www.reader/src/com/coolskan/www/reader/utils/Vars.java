package com.coolskan.www.reader.utils;

public class Vars {
	
	public static final String HREF="<a href=\"";

	public static final String URL_VENTES="http://www.leboncoin.fr/ventes_immobilieres/";
	public static final String URL_LOC="http://www.leboncoin.fr/locations/";

	public static final String OFFRES="offres/";

	public static final String PARAM="?f=a&th=1";

	public static final String CA_STOP="\">";
	
//  ?f=a&th=1
	public static final String URL_REGION_PARAM="/?f=a&th=1&sqs=19"; // immobilier >500m�

	public static final String DEPARTEMENT="departement";
	public static final String PRIX="prix";
	public static final String SURFACE="surface";
	public static final String PIECE="piece";
	public static final String CODE_POSTAL="postal";
	public static final String TYPE_BIEN="typedebiens";
	public static final String META_TITLE="<meta name=\"title\"";
	public static final String META_DESC="<meta name=\"description\"";
	
	public static final String COND_LOYER_MAX="&mrs";
	public static final String COND_LOYER_MIN="&mre=";
	
	// kvdepartement=30;
	// kvprix=345;
	// kvsurface=47;
	// kvpiece=3;
	// kvpostal=30160;
	// kvtypedebiens=2;

	// kvregion=0;
	// kvrecherche=;
	// kvzone=0;
	// kvlocal=0;
	
	public static final String COLUMN_HEADER=
			Vars.DEPARTEMENT+"\t"+
			Vars.PRIX+"\t"+
			Vars.SURFACE+"\t"+
			Vars.PIECE+"\t"+
			Vars.CODE_POSTAL+"\t"+
			Vars.TYPE_BIEN+"\t"+
			Vars.META_TITLE+"\t"+
			Vars.META_DESC+"\tKEY\tURL";
	
}
