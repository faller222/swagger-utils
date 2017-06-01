package uy.com.antel.swagger.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by e520304 on 01/06/2017.
 */
public class ToScala {

  public static void clases(Map<String, Class> nombres) {
    for (Class aClass : nombres.values()) {
      printClass(aClass);
    }
  }

  private static void printClass(Class clazz) {

    System.out.println("    ");
    System.out.println("case class " + clazz.getSimpleName() + "(");

    String coma = "";

    for (Field field : clazz.getDeclaredFields()) {
      System.out.print(coma + printField(field));

      if ("".equals(coma)) {
        coma = ",\n ";
      }
    }

    System.out.println(")");
  }

  private static String printField(Field field) {
    String name = field.getName();
    Class type = field.getType();
    switch (type.getName()) {
      case "java.lang.String":
        return name + ": String";
      case "int":
        return name + ": Int";
      case "long":
        return name + ": Int";
      case "java.lang.Integer":
        return name + ": Int";
      case "java.math.BigDecimal":
        return name + ": Double";
      case "java.util.List":
        return name + ": Seq[ /*FIXME*/ ]";
      case "javax.xml.datatype.XMLGregorianCalendar":
        return name + ": DateTime";
      default:

        return name + ": " + type.getSimpleName();
    }
  }
}
