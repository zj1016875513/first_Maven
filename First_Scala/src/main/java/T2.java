public class T2 {
    public static void main(String[] args) {
        father a = new son();
        System.out.println(a.name);
        a.action();
        a.work();
//        a.study();//报错   编译时看左边，运行时看右边，运行时如果有重写的方法，那么就运行那个重写之后的方法

        System.out.println(a instanceof father);
        System.out.println(a instanceof son);


    }
}

class father{
    public String name="father";
    public void action(){
        System.out.println(name+"在工作");
    }
    public void work(){
        System.out.println("努力赚钱");
    }
}

class son extends father {
    public String name = "son";

    @Override
    public void action() {
        System.out.println(name + "在玩耍");
    }

    public void study(){
        System.out.println("努力学习");
    }
}