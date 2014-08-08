package com.coolskan.www.reader.launcher;

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import com.coolskan.www.reader.utils.InterfacesOperations;
import com.coolskan.www.reader.utils.StreamDumper;
import com.coolskan.www.reader.utils.Vars;

public class Tester {
	private static final Logger logger = Logger.getLogger(Tester.class);

	private static final boolean DUMP_DEBUG=false;
	private static final int SPEED=100; // nombre de milli � attendre entre 2 requetes.

	private static final String FILE_TXT=".txt";
	private static final String FILE_HTML=".htm";
	private static final String FILE_TARGET_DIR="/Users/thierry/tmp/lbc/";

	public static void main(String[] args) {
		logger.info(Vars.COLUMN_HEADER);

		try {
			
			StreamDumper.dump(FILE_TARGET_DIR+"auvergne"                  +FILE_TXT, loadRegion("auvergne"                  ));
		} catch (Exception e) {
			logger.error("",e);
		}

	}

	private static String loadRegion(String _region) {
		//http://www.leboncoin.fr/ventes_immobilieres/offres/midi_pyrenees/?f=a&th=1&ret=1&ret=3&ret=5 //maison+terrain+autre &ret=1&ret=3&ret=5
		//http://www.leboncoin.fr/ventes_immobilieres/offres/midi_pyrenees/?f=a&th=1
		//http://www.leboncoin.fr/ventes_immobilieres/offres/languedoc_roussillon/?f=a&th=1

		String totalRegion="";

		String dumpFileName=null;
		String urlRegion=Vars.URL_VENTES+Vars.OFFRES+_region+Vars.URL_REGION_PARAM;
		int i=0;
		dumpFileName = FILE_TARGET_DIR+_region+String.valueOf(i)+FILE_TXT;

		String pageRegion=loadPage(dumpFileName, urlRegion);
		while(pageRegion!=null) {
			logger.info("Parsing: "+urlRegion);
			totalRegion=totalRegion+parseRegionPage(pageRegion);
			urlRegion=nextPage(pageRegion);
			i=i+1;
			dumpFileName = FILE_TARGET_DIR+_region+String.valueOf(i)+FILE_TXT;
			pageRegion=loadPage(dumpFileName, urlRegion);
		}
		return totalRegion;
	}

	private static String nextPage(String pageRegion) {
		//<a href="http://www.leboncoin.fr/ventes_immobilieres/offres/languedoc_roussillon/?o=2">Page suivante &raquo;</a>

		String searchSuivante="\">Page suivante &raquo;</a>";
		int indexPageSuivante=pageRegion.indexOf(searchSuivante, 0);
		if(indexPageSuivante<0) return null;
		int recule=indexPageSuivante-1;
		while(recule>0&&!pageRegion.substring(recule).startsWith("http")) {
			recule=recule-1;
		}
		if(recule>0) {
			return pageRegion.substring(recule,indexPageSuivante).replaceAll("&amp;", "&");
		}
		return null;
	}

	private static String parseRegionPage(String analyser) {
		//<a href="http://www.leboncoin.fr/ventes_immobilieres/212118498.htm?ca=13_s"><img src="http://193.164.196.50/thumbs/088/0886422007.jpg" border="0" alt="Maison type 4 &agrave; Rodilhan"></a>
		//<a href="http://www.leboncoin.fr/ventes_immobilieres/212118498.htm?ca=13_s">Maison type 4 &agrave; Rodilhan</a>
		if(analyser==null) return null;
		String pageContain="";
		int searchFrom=0;
		String lastAnnonce="";
		int indexRef=analyser.indexOf(Vars.HREF+Vars.URL_VENTES, searchFrom);
		int hrefLong=(Vars.HREF+Vars.URL_VENTES).length();
		while (indexRef>0&&!analyser.substring(indexRef).startsWith(Vars.HREF+Vars.URL_VENTES+Vars.OFFRES)) {
			int indexStop=analyser.indexOf(Vars.CA_STOP, indexRef);
			if(indexStop>0) {
				String annonce=analyser.substring(indexRef+hrefLong, indexStop);
				if(!lastAnnonce.equals(annonce)) {
					pageContain=pageContain+loadAnnonce(annonce)+"\n";
					lastAnnonce=annonce;
				}
			}
			//aller sur la ref suivante � analyser
			searchFrom=indexRef+hrefLong;
			indexRef=analyser.indexOf(Vars.HREF+Vars.URL_VENTES, searchFrom);
		}
		return pageContain;
	}

	private static String loadAnnonce(String _annonce) {
		//http://www.leboncoin.fr/ventes_immobilieres/183325207.htm?ca=13_s
		String fileName=null;
		String urlTxt=Vars.URL_VENTES+_annonce;
		String annonceId=_annonce.split("\\.")[0];
		fileName = FILE_TARGET_DIR+annonceId+FILE_HTML;
		String pageAnnonce=loadPage(fileName, urlTxt);
		String annonce=parseAnnonce(pageAnnonce,annonceId,urlTxt);
		return annonce;
	}

	protected static String loadPage(String fileName, String urlTxt) {
		if(urlTxt==null) return null;
		try {
			Thread.sleep(SPEED);
		} catch (InterruptedException e) {
			logger.error("",e);
		}
		URL url=null;
		InputStream stream=null;
		String analyser=null;
		try {
			url = new URL(urlTxt);
			stream = url.openStream();
			analyser = StreamDumper.dump(stream);
			if(DUMP_DEBUG) {
				StreamDumper.dump(fileName, analyser);
			}
		} catch (Exception e) {
			logger.error("urlTxt:"+urlTxt,e);
		}
		finally {
			InterfacesOperations.closeAll(stream);
		}
		return analyser;
	}


	private static String extract(String analyser,String searched,int startIndex,String stopString) {
		if(analyser==null) return null;
		String valeur="";
		int indexRefStart=analyser.indexOf(searched, startIndex);
		if(indexRefStart>0) {
			int indexRefEqual=analyser.indexOf("=", indexRefStart+searched.length());
			if(indexRefEqual>0) {
				int indexRefStop=analyser.indexOf(stopString, indexRefEqual);
				if(indexRefStop>0) {
					valeur=analyser.substring(indexRefEqual+1, indexRefStop);
				}
			}
		}
		return valeur;
	}

	private static String parseAnnonce(String analyser,String annonceId,String url) {
		if(analyser==null) return null;
		//      kvdepartement=30;kvregion=0;kvrecherche=;kvzone=0;kvlocal=0;kvprix=248000;kvsurface=105;kvpiece=5;kvpostal=30200;kvtypedebiens=1
		//		<meta name="title" content="Terrain ? batir: id?al nature, p?che, champignons Ventes immobili?res Corr?ze - leboncoin.fr">
		//		<meta name="description" content="A 3 km de Egletons (5000 habitants, autoroute A89), terrain ? b?tir en pente de 1770 m2 avec certificat d'urbanisme. Eau et ?lectricit? en fa?ade. Orientation est-ouest. Situ? dans une zone habit?e, ce terrain est id?al pour construire une r?sidence princ">
		
		String dpt=extract(analyser,"kv"+Vars.DEPARTEMENT,0,";");
		String prix=extract(analyser,"kv"+Vars.PRIX,0,";");
		String surface=extract(analyser,"kv"+Vars.SURFACE,0,";");
		String piece=extract(analyser,"kv"+Vars.PIECE,0,";");
		String postal=extract(analyser,"kv"+Vars.CODE_POSTAL,0,";");
		String type=extract(analyser,"kv"+Vars.TYPE_BIEN,0,";");
		String metat=extract(analyser,Vars.META_TITLE,0,">");
		String metad=extract(analyser,Vars.META_DESC,0,">");
		
		String result=dpt+"\t"+prix+"\t"+surface+"\t"+piece+"\t"+postal+"\t"+type+"\t"+metat+"\t"+metad+"\t"+annonceId+"\t"+url;
		
//		if(Convertion.toLong(surface)>=10000) {
//			try {
//				StreamDumper.dump(FILE_TARGET_DIR+annonceId+FILE_HTML, analyser);
//			} catch (Exception e) {
//				logger.error("annonceId:"+annonceId,e);
//			}
//		}
		
		

//		<table cellpadding="0" class="AdviewContent">
//		<tr>
//			<td valign="top">
//				<span class="lbcAd_text">A 3 km de Egletons (5000 habitants, autoroute
//A89),<br>terrain ? b?tir en pente de 1770 m2 avec
//certificat d'urbanisme. Eau et ?lectricit? en
//fa?ade.<br>Orientation est-ouest.<br>Situ? dans une zone habit?e, ce terrain est id?al
//pour construire une r?sidence principale ou
//secondaire pour un amateur de nature, p?che et
//champignons. Ruisseau, lac, bois ? proximit?.
//Passage d'un ancien canal d'alimentation d'un
//moulin au fond du terrain.<br>Ce terrain est propre et r?guli?rement entretenu.<br>Prix: 10 000 ? ? d?battre<br>Contact: 0557846517 de 12H ? 19H.</span>
//				<br/>
//				</td>
//		</tr>
//	</table>
		
//		<div class="lbcAdImages">
//		
//		<div class="ad_pict" id="display_image" style=""><img src="http://193.164.196.60/images/020/0206412913.jpg" id="main_image" alt="Terrain ? batir: id?al nature, p?che, champignons" title="Terrain ? batir: id?al nature, p?che, champignons" onclick="next_image();return false;" onmouseover="this.style.cursor='hand'" onload="show_hidden_elements();return false;" /></div>
//	
//	<div class="thumb_images">
//		<div id="thumb0" class="ad_thumb  ad_border_solid_black" onclick="counter = 0 + 1; set_alt_title('thumb'); showLargeImage('http://193.164.196.60/images/020/0206412913.jpg');thumbnailBorder(this, 2)">
//				<table class="clean_table shadowed_thumb" cellpadding="0" cellspacing="0">
//					<tbody>
//						<tr>
//							<td><img src="http://193.164.196.40/img/thumb_left_top.gif" alt="" /></td>
//							<td class="top_middle" align="left" valign="bottom"> </td>
//							<td> </td>
//						</tr>
//						<tr>
//							<td class="middle_left"> </td>
//							<td><img src="http://193.164.196.60/thumbs/020/0206412913.jpg" alt="" /></td>
//							<td class="single_middle_right" valign="top"><img src="http://193.164.196.40/img/thumb_single_right_top.gif" alt="" /></td>
//						</tr>
//						<tr>
//							<td> </td>
//							<td class="single_bottom_center" valign="top"><img src="http://193.164.196.40/img/thumb_single_left_bottom.gif" alt="" /></td>
//							<td valign="top"><img src="http://193.164.196.40/img/thumb_single_right_bottom.gif" alt="" /></td>
//						</tr>
//					</tbody>
//				</table>
//			</div>
//		<div id="thumb1" class="ad_thumb " onclick="counter = 1 + 1; set_alt_title('thumb'); showLargeImage('http://193.164.196.30/images/021/0214936232.jpg');thumbnailBorder(this, 2)">
//				<table class="clean_table shadowed_thumb" cellpadding="0" cellspacing="0">
//					<tbody>
//						<tr>
//							<td><img src="http://193.164.196.40/img/thumb_left_top.gif" alt="" /></td>
//							<td class="top_middle" align="left" valign="bottom"> </td>
//							<td> </td>
//						</tr>
//						<tr>
//							<td class="middle_left"> </td>
//							<td><img src="http://193.164.196.30/thumbs/021/0214936232.jpg" alt="" /></td>
//							<td class="single_middle_right" valign="top"><img src="http://193.164.196.40/img/thumb_single_right_top.gif" alt="" /></td>
//						</tr>
//						<tr>
//							<td> </td>
//							<td class="single_bottom_center" valign="top"><img src="http://193.164.196.40/img/thumb_single_left_bottom.gif" alt="" /></td>
//							<td valign="top"><img src="http://193.164.196.40/img/thumb_single_right_bottom.gif" alt="" /></td>
//						</tr>
//					</tbody>
//				</table>
//			</div>
//		
//	</div>
//
//</div>
		
	
		return result;
	}


}
