package cn.net.loveit.home;

public class B extends A{
    @Override
    boolean a() {
//        super.a();
        System.out.println("BBBBB");
        return true;
    }
}
class D{
    int a;
}

class C{
    public static char[] a=new char[5];

    public static void main(String[] args) {
        A b = new B();
        b.c();
        System.out.println(a);
        System.out.println(String.valueOf(a[0]));


        D d = new D();
        d.a=3;
        D d2=d;
        System.out.println(d==d2);
        System.out.println(d.equals(d2));
        String st="f";
        String str="ff";
        String str1="f"+st;
        String str2="ff";
        System.out.println(str==str1);
        System.out.println(str==str2);

        String ss="Hello";
        String ss1="Hel";
        String ss2=ss.substring(0,3);
        System.out.println(ss2);
        System.out.println(ss2==ss1);
        System.out.println((ss1+"lo")==ss);
        System.out.println(ss.offsetByCodePoints(0,1));
        System.out.println(ss.codePointAt(1));
    }
}


