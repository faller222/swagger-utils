package uy.com.antel.swagger.utils;


import java.lang.reflect.Field;
import java.util.Map;

public class ToYaml {

  public static void clases(Map<String, Class> nombres) {
    for (Class aClass : nombres.values()) {
      printClass(aClass);
    }
  }

  private static void printClass(Class clazz) {

    System.out.println("    ");
    System.out.println("  " + clazz.getSimpleName() + ":");
    System.out.println("    type: object");
    System.out.println("    properties:");

    for (Field field : clazz.getDeclaredFields()) {
      printField(field);
    }
  }

  private static void printField(Field field) {
    String name = field.getName();
    Class type = field.getType();
    System.out.println("      " + name + ":");
    switch (type.getName()) {
      case "java.lang.String":
        System.out.println("        type: string");
        break;
      case "long":
        System.out.println("        type: integer");
        System.out.println("        format: int32");
        break;
      case "int":
        System.out.println("        type: integer");
        System.out.println("        format: int64");
        break;
      case "java.lang.Integer":
        System.out.println("        type: integer");
        System.out.println("        format: int64");
        break;
      case "java.math.BigDecimal":
        System.out.println("        type: number");
        break;
      case "java.util.List":
        System.out.println("        type: array");
        System.out.println("        items: {$ref: '#/definitions/FIXME'}");
        break;
      case "javax.xml.datatype.XMLGregorianCalendar":
        System.out.println("        type: string");
        System.out.println("        format: date-time");
        break;
      default:

        System.out.println("        $ref: '#/definitions/" + type.getSimpleName() + "'");
    }
  }
}
