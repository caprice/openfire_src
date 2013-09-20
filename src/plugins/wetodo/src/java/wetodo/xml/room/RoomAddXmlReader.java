package wetodo.xml.room;

import org.dom4j.Element;

import java.util.List;

public class RoomAddXmlReader {

    public static String getSubject(Element lacoolElement) {
        List<Element> fieldElements = lacoolElement.element("x").elements("field");
        for (Element fieldElement : fieldElements) {
            if ("muc#roominfo_subject".equals(fieldElement.attribute("var").getValue())) {
                return fieldElement.element("value").getStringValue();
            }
        }
        return null;
    }
}
