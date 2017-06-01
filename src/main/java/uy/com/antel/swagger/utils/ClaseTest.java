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


        ToYaml.clases(map);

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


}
