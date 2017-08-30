package net.orange_box.storebox.example.ormlite;

import net.orange_box.storebox.StoreBoxSE;
import net.orange_box.storebox.engines.HashMapEngine;

public class Main {

    private static ValueTypesInterface uut;

    public static void main (String[] argc) {
        uut = StoreBoxSE.create(new HashMapEngine(), ValueTypesInterface.class);

        System.out.println("TODO");

        System.out.println("testBoolean");
        System.out.println(uut.getBoolean(true));
        System.out.println(!uut.getBoolean());
        System.out.println(!uut.getBooleanObj(null));
        System.out.println(!uut.getBooleanObj());
        uut.setBoolean(true);
        System.out.println(uut.getBoolean());

        System.out.println("testFloat");
        System.out.println(1F == uut.getFloat(1F));
        System.out.println(0F == uut.getFloat());
        System.out.println(0F == uut.getFloatObj(null));
        System.out.println(0F == uut.getFloatObj());
        uut.setFloat(1.0F);
        System.out.println(1.0F == uut.getFloat());

        System.out.println("testInt");
        System.out.println(1 == uut.getInt(1));
        System.out.println(0 == uut.getInt());
        System.out.println(0 == uut.getIntObj(null));
        System.out.println(0 == uut.getIntObj());
        uut.setFloat(1);
        System.out.println(1 == uut.getFloat());

        System.out.println("testLong");
        System.out.println(0L == uut.getLong());
        uut.setLong(1L);
        System.out.println(1L == uut.getLong());

        System.out.println("testString");
        System.out.println(null == uut.getString());
        System.out.println("string".equals(uut.getString("string")));
        uut.setString("string");
        System.out.println("string".equals(uut.getString()));

        System.out.println("testStringSet");
        System.out.println();
    }

}
