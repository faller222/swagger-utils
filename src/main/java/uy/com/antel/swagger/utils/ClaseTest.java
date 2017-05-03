package uy.com.antel.swagger.utils;


import uy.com.antel.ns.schema.sid.extension.common_v1.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClaseTest {


    public static void main(String[] args) {

        Map<String, Class> map = new HashMap<>();
        rescatarClases(OrderType.class, map);
        System.out.println(" --- ");
        rescatarClases(RelatedOrderType.class, map);
        rescatarClases(ContactType.class, map);
        rescatarClases(RelatedPartyType.class, map);
        rescatarClases(RelatedAccountType.class, map);
        rescatarClases(LocationType.class, map);
        rescatarClases(HandlingType.class, map);
        rescatarClases(CharacteristicEnrichedType.class, map);
        rescatarClases(OrderItemType.class, map);
        System.out.println(" --- ");
        rescatarClases(ContactMechanismType.class, map);
        rescatarClases(DeliveryReferenceType.class, map);
        rescatarClases(PsrType.class, map);
        System.out.println(" --- ");
        rescatarClases(ContactTimeType.class, map);
        rescatarClases(CharacteristicEnrichedType.class, map);
        rescatarClases(PsrRelatedToType.class, map);
        System.out.println(" --- ");


        System.out.println(map.size());


        toYamlClases(map);

    }


    private static void rescatarClases(Class clazz, Map<String, Class> nombres) {

        String canonicalName = clazz.getCanonicalName();
        nombres.put(canonicalName, clazz);

        for (Field field : clazz.getDeclaredFields()) {
            String fCanonicalName = field.getType().getCanonicalName();

            //Impirmo los atributos que son colleciones de
            if (Collection.class.isAssignableFrom(field.getType())) {
                //System.out.println(canonicalName + " -> " + field.getName());
            }

            if (!nombres.containsKey(fCanonicalName)) {
                if (fCanonicalName.startsWith("uy.com.antel")) {
                    rescatarClases(field.getType(), nombres);
                }
            }
        }
    }

    private static void toYamlClases(Map<String, Class> nombres) {
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
