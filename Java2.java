package dfdad1;
class firm{
    private String firmNo;//firm number
    private String firmName;//name
    private String lag;//language
    private String coun ;//country
    private String dura;//duration
    private String dire;//director
    public firm(){}
    public firm(String firmNo,String firmName,String lag,String coun,String dura,String dire){
        this.firmNo = firmNo;
        this.firmName = firmName;
        this.lag = lag;
        this.coun = coun;
        this.dura = dura;
        this.dire = dire;
    }
    public void setfirmNo(String firmNo){
        this.firmNo = firmNo;
    }
    public String getfirmNo() {
        return this.firmNo;
    }
    public void setfirmName(String firmName){
        this.firmName = firmName;
    }
    public String getfirmName(){
        return this.firmName;
    }
    public void setlag(String lag){
        this.lag = lag;
    }
    public String getlag(){
        return this.lag;
    }
    public void setcoun(String coun){
        this.coun = coun;
    }
    public String getcoun(){
        return this.coun;
    }
    public void setdura(String dura) {
        this.dura = dura;
    }
    public String getdura() {
        return dire;
    }
    public void setdire(String dire) {
        this.dire = dire;
    }
    public String getdire() {
        return dire;
    }
    public String getInfo(){
        return "firm number:" + this.firmNo + "\n" +
                "firm name:" + this.firmName + "\n" +
                "language:" + this.lag + "\n" +
                "country:" + this.coun + "\n" +
                "duration£º" + this.dura + "\n" +  
                "director"  +  this.dire;
    }
	 
}

 
