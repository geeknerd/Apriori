public class Transaction {
	private String abbrev;
	private String rep;
	private String locality;
	private String map_ref;
	private String latitude;
	private String altitude;
	private String rainfall;
	private String frosts;
	private String year;
	private String sp;
	private String pmcno;
	private String dbh;
	private String ht;
	private String surv;
	private String vig;
	private String ins_res;
	private String stem_rm;
	private String crown_fm;
	private String brnch_fm;
	private int id;
	private String utility;

	public Transaction() {
		
	}

	public String toString(){
		return "Transaction "+this.id+" "+this.abbrev+" "+this.rep+" "+this.locality+" "+this.map_ref+" "+this.latitude+" "+this.latitude+" "+this.altitude+" "+this.rainfall+" "+this.frosts+" "+this.year+" "+this.sp+" "+this.pmcno+" "+this.dbh+" "+this.ht+" "+this.surv+" "+this.vig+" "+this.ins_res+" "+this.stem_rm+" "+this.crown_fm+" "+this.brnch_fm+" "+this.utility;
	}
	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getMap_ref() {
		return map_ref;
	}

	public void setMap_ref(String map_ref) {
		this.map_ref = map_ref;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getRainfall() {
		return rainfall;
	}

	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	public String getFrosts() {
		return frosts;
	}

	public void setFrosts(String frosts) {
		this.frosts = frosts;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getPmcno() {
		return pmcno;
	}

	public void setPmcno(String pmcno) {
		this.pmcno = pmcno;
	}

	public String getDbh() {
		return dbh;
	}

	public void setDbh(String dbh) {
		this.dbh = dbh;
	}

	public String getHt() {
		return ht;
	}

	public void setHt(String ht) {
		this.ht = ht;
	}

	public String getSurv() {
		return surv;
	}

	public void setSurv(String surv) {
		this.surv = surv;
	}

	public String getVig() {
		return vig;
	}

	public void setVig(String vig) {
		this.vig = vig;
	}

	public String getIns_res() {
		return ins_res;
	}

	public void setIns_res(String ins_res) {
		this.ins_res = ins_res;
	}

	public String getStem_rm() {
		return stem_rm;
	}

	public void setStem_rm(String stem_rm) {
		this.stem_rm = stem_rm;
	}

	public String getCrown_fm() {
		return crown_fm;
	}

	public void setCrown_fm(String crown_fm) {
		this.crown_fm = crown_fm;
	}

	public String getBrnch_fm() {
		return brnch_fm;
	}

	public void setBrnch_fm(String brnch_fm) {
		this.brnch_fm = brnch_fm;
	}
	
	
}


