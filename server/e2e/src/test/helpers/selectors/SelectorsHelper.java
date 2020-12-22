package helpers.selectors;

public class SelectorsHelper {
    static String parentContains(String parent,String child,String...otherChildren) {
        StringBuilder builder=new StringBuilder();
        builder.append(parent+" "+child);
        for(String nestedChild : otherChildren) {
            builder.append(" "+nestedChild);
        }
        return builder.toString();
    }

    static String combine(String parent,String child,String...otherChildren) {
        StringBuilder builder=new StringBuilder();
        builder.append(parent+child);
        for(String nestedChild : otherChildren) {
            builder.append(nestedChild);
        }
        return builder.toString();
    }
    static String cssClass(String clazz) {
        return "."+clazz;
    }
    static String metaId(String id) {
        return "[data-id='"+id+"']";
    }
}
