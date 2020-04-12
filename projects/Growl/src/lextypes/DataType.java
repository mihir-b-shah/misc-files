
package lextypes;

import java.util.regex.Pattern;

public final class DataType {
    public static final Pattern TYPE_REGEX = Pattern.compile("((struct\\s+[\\w&&\\D][\\w]*)|"
                + "(int)|(long)|(char)|(float)|(bool)|(void))(\\*)*");
    int ptrLvl;
    public BaseType base;

    private DataType(int ptrLvl) {
        this.ptrLvl = ptrLvl;
    }

    public static enum BaseType {
        VOID, BOOL, I8, I32, I64, F64, STRUCT;
    }

    public static DataType createDataType(String s) {
        int idx;
        DataType ret;
        if((idx = s.indexOf('*')) == -1) {
            ret = new DataType(0);
        } else {
            ret = new DataType(s.length()-idx);
            s = s.substring(0, idx);
        }

        switch(s) {
            case "bool":
                ret.base = BaseType.BOOL;
                break;
            case "char":
                ret.base = BaseType.I8;
                break;
            case "int":
                ret.base = BaseType.I32;
                break;
            case "long":
                ret.base = BaseType.I64;
                break;
            case "float":
                ret.base = BaseType.F64;
                break;
            default:
                if(s.substring(0,6).equals("struct")) {
                    ret.base = BaseType.STRUCT;
                } else {
                    ret.base = BaseType.VOID;
                }  
                break;
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(base.name());
        if(ptrLvl > 0) {
            sb.append('_');
            sb.append(ptrLvl);
            sb.append("_PTR");
        }
        return sb.toString();
    }
}
   