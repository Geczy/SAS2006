package server.org.engine.character.skilling.fletching;

import server.org.engine.character.Client;
 

 
public class FletchingHandler {
    
    public enum ShortBow {
        SHORTBOW(1511,1,5,50,841),
        OAK(1521,20,17,54,843),
        WILLOW(1519,35,33,60,849),
        MAPLE(1517,50,50,64,853),
        YEW(1515,65,68,68,857),
        MAGIC(1513,80,83,72,861);
         
        private int logType,req,exp,bowUnStr,bowStr;
         
        public int getLog(){
            return logType;
        }
        public int getReq(){
            return req;
        }
        public int getExp(){
            return exp;
        }
        public int getBowU(){
            return bowUnStr;
        }
        public int getBow(){
            return bowStr;
        }
         
        private ShortBow(int log,int req,int exp,int bow,int bow2){
            this.logType = log;
            this.req = req;
            this.exp = exp;
            this.bowUnStr = bow;
            this.bowStr = bow2;
        }
         
        public static ShortBow forID(int logId){
            for(ShortBow bow : ShortBow.values())
                if(bow.getLog() == logId)
                    return bow;
            return null;
        }
         
        public static ShortBow forUID(int bowU){
            for(ShortBow bow : ShortBow.values())
                if(bow.getBowU() == bowU)
                    return bow;
            return null;
        }
    }
     
    public enum LongBow {
        SHORTBOW(1511,10,10,48,839),
        OAK(1521,25,25,56,845),
        WILLOW(1519,40,42,60,847),
        MAPLE(1517,55,58,62,851),
        YEW(1515,65,70,66,855),
        MAGIC(1513,85,92,70,859);
         
        private int logType,req,exp,bowU,bow;
         
        public int getLog(){
            return logType;
        }
        public int getReq(){
            return req;
        }
        public int getExp(){
            return exp;
        }
        public int getBowU(){
            return bowU;
        }
        public int getBow(){
            return bow;
        }
         
        private LongBow(int log,int req,int exp,int bow,int bow2){
            this.logType = log;
            this.req = req;
            this.exp = exp;
            this.bowU = bow;
            this.bow = bow2;
        }
         
        public static LongBow forID(int logId){
            for(LongBow bow : LongBow.values())
                if(bow.getLog() == logId)
                    return bow;
            return null;
        }
     
        public static LongBow forUID(int bowU){
            for(LongBow bow : LongBow.values())
                if(bow.getBowU() == bowU)
                    return bow;
            return null;
        }
    }
     
    public enum CrossBow {
        BRONZE(1511,9,6,12,9440,9420,9454,9174),
        IRON(1519,24,16,32,9444,9423,9457,9177),
        MITHRIL(1517,54,32,64,9448,9427,9461,9181),
        RUNE(1515,69,50,100,9452,9431,9465,9185);
         
        private int logType,req,exp1,exp2,stock,limbs,bowU,bow;
         
        public int getLog(){
            return logType;
        }
        public int getReq(){
            return req;
        }
        public int getExp1(){
            return exp1;
        }
        public int getExp2(){
            return exp2;
        }
        public int getStock(){
            return stock;
        }
        public int getLimbs(){
            return limbs;
        }
        public int getBow(){
            return bow;
        }
        public int getBowU(){
            return bowU;
        }
         
        private CrossBow(int log,int req,int exp,int exp2,int stock,int limbs,int bowU,int bow){
            this.logType = log;
            this.req = req;
            this.exp1 = exp;
            this.exp2 = exp2;
            this.stock = stock;
            this.limbs = limbs;
            this.bowU = bowU;
            this.bow = bow;
        }
         
        public static CrossBow forID(int logId){
            for(CrossBow bow : CrossBow.values())
                if(bow.getLog() == logId)
                    return bow;
            return null;
        }
     
        public static CrossBow forUID(int bowU){
            for(CrossBow bow : CrossBow.values())
                if(bow.getBowU() == bowU)
                    return bow;
            return null;
        }
         
        public static CrossBow forLimbID(int limb){
            for(CrossBow bow : CrossBow.values())
                if(bow.getLimbs() == limb)
                    return bow;
            return null;
        }
    }
     
    public enum Arrows{
        BRONZE(1,3,39,882),
        IRON(15,4,40,884),
        STEEL(30,6,41,886),
        MITHRIL(45,9,42,888),
        ADAMANT(60,10,43,890),
        RUNE(75,14,44,892),
        DRAGON(90,16,11237,11212);
         
        private int req,exp,tips,arrow;
         
        public int getTips(){
            return tips;
        }
         
        public int getReq(){
            return req;
        }
         
        public int getExp(){
            return exp;
        }
         
        public int getArrow(){
            return arrow;
        }
         
        private Arrows (int req,int exp,int tips,int arrow){
            this.req = req;
            this.exp = exp;
            this.tips = tips;
            this.arrow = arrow;
        }
         
        public static Arrows forId(int tips){
            for(Arrows arrow : Arrows.values())
                if(arrow.getTips() == tips)
                    return arrow;
            return null;
        }
    }
     
    public enum Bolts{
        BRONZE(9375,887,9,1),
        IRON(9377,9140,39,2),
        STEEL(9378,9141,46,4),
        MITHRIL(9379,9142,54,5),
        ADAMANT(9380,9143,61,7),
        RUNE(9381,9144,69,10);
         
        private int input,output,req,exp;
         
        public int getInput(){
            return input;
        }
        public int getOutput(){
            return output;
        }
        public int getReq(){
            return req;
        }
        public int getExp(){
            return exp;
        }
         
        private Bolts(int item,int item2,int req,int exp){
            this.input = item;
            this.output = item2;
            this.req = req;
            this.exp = exp;
        }
         
        public static Bolts forId(int input){
            for(Bolts bolt : Bolts.values())
                if(bolt.getInput() == input)
                    return bolt;
            return null;
        }
    }
     
    public static void appendFletch(Client c,int ID,String type) {
        if(type.equalsIgnoreCase("arrow")){
            if(ID == c.feather){
                c.fletchType = ID;
                c.fletchThis = "headlessarrow";
                c.getDH().sendDialogues(8, -1);
            } else if (Arrows.forId(ID) != null) {
                c.fletchType = ID;
                c.fletchThis = "arrows";
                c.getDH().sendDialogues(8, -1);
            }
        }
        if(type.equals("limb")){
            c.fletchType = ID;
            c.fletchThis = "limb";
            c.getDH().sendDialogues(8, -1);
        }
        if(type.equalsIgnoreCase("string")){
            if(ShortBow.forUID(ID) != null){
                c.fletchType = ID;
                c.fletchThis = "stringShortbow";
                c.getDH().sendDialogues(8, -1);
            } else if(LongBow.forUID(ID) != null){
                c.fletchType = ID;
                c.fletchThis = "stringLongbow";
                c.getDH().sendDialogues(8, -1);
            } else if(CrossBow.forUID(ID) != null){
                c.fletchType = ID;
                c.fletchThis = "stringCrossbow";
                c.getDH().sendDialogues(8, -1);
            }
        }
        if(type.equalsIgnoreCase("useitem")){
            c.fletchType = ID;
            c.getDH().sendDialogues(7, -1);
        } else if (type.equalsIgnoreCase("clickingbutton")){
            int action = c.dialogueAction;
            switch(ID){
            case 9190:
                if(action == 15){
                    c.fletchThis = "shaft";
                    c.getDH().sendDialogues(8,-1);
                }else
                    Fletching.startFletching(c,1);
                break;
            case 9191:
                if(action == 15){
                    c.fletchThis = "stock";
                    c.getDH().sendDialogues(8,-1);
                }else
                    Fletching.startFletching(c,5);
                break;
            case 9192:
                if(action == 15){
                    c.fletchThis = "shortbow";
                    c.getDH().sendDialogues(8,-1);
                }else
                    Fletching.startFletching(c,10);
                break;
            case 9193:
                if(action == 15){
                    c.fletchThis = "longbow";
                    c.getDH().sendDialogues(8,-1);
                }else
                    Fletching.startFletching(c,28);
                break;
            }
        }
    }
         
}