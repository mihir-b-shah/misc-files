   
public class Log {
    
    private final int slang;
    private final int dlang;
    private final String init;
    private final String trans;
    private final String corr;
    
    public Log(int s, int d, String i, String t, String c) {
        slang = s;
        dlang = d;
        init = i;
        trans = t;
        corr = c;
    }
    
    public String getTrans() {
        return trans;
    }
    
    public int getSLang() {
        return slang;
    }
    
    public int getDLang() {
        return dlang;
    }
    
    public String getCorr() {
        return corr;
    }
    
    public String write() {
        return String.format("%d\t%d\t%s\t%s\t%s%n", slang, dlang, init, 
                trans, corr);
    }
    
    @Override
    public String toString() {
        return String.format("Language %d to Language %d: User input: %s, "
                + "Gen output: %s, Corrected output: %s", slang, dlang, 
                init, trans, corr);
    }
}
