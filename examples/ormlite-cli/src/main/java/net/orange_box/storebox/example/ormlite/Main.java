package net.orange_box.storebox.example.ormlite;

import net.orange_box.storebox.StoreBoxSE;

public class Main {

    private static ValueTypesInterface uut;

    public static void main (String[] argc) {
        uut = StoreBoxSE.create(new OrmLiteEngine(), ValueTypesInterface.class);

        System.out.println("TODO");

        System.out.println("testBoolean");
        System.out.println(!uut.getBoolean());
        uut.setBoolean(true);
        System.out.println(uut.getBoolean());

        System.out.println("testFloat");
        System.out.println(0F == uut.getFloat());
        uut.setFloat(1.0F);
        System.out.println(1.0F == uut.getFloat());

        System.out.println("testInt");
        System.out.println(0 == uut.getFloat());
        uut.setFloat(1);
        System.out.println(1 == uut.getFloat());

        System.out.println("testLong");
        System.out.println(0L == uut.getFloat());
        uut.setFloat(1L);
        System.out.println(1L == uut.getFloat());

        System.out.println("testString");
        System.out.println(null == uut.getString());
        uut.setString("string");
        System.out.println("string".equals(uut.getString()));

        System.out.println("testStringSet");
        System.out.println();
    }

}
