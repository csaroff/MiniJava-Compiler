class Main{
    public static void main(String[] args){
        //if(true){
        System.out.println(
                new A().f(1));
        //}else{
        //    System.out.println(0);
        //}
    //System.out.println(0);
    }
}
class A{
    //int a;
    int y;
    public int f(int input){
        //int x;
        //x=0;
        //y=1;
        //while(x<10){
        //    x = this.f(1) + 1;
        //}
        System.out.println(input);
        return this.f(1 + input);
    }
}
//class B extends A{
//
//}
