import java.util.Scanner;

public class CoffeeMachine {


    public static void main(String[] args) {

        while(Action.process()) {
        }


    }
}

final class Data {

    private static int water;
    private static int milk;
    private static int beans;
    private static int money;
    private static int cups;

    static {

        water = 400;
        milk = 540;
        beans = 120;
        money = 550;
        cups = 9;

    }

    static void printAllData() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(money + " of money");


    }

    static void buyCoffee(String x){
        int i = 0;
        if (!x.equals("back")) {
            try {
                i = Integer.parseInt(x);
            } catch (Exception e) {
                System.out.println("not correct input");
            }

            switch (i) {
                case 1:
                    espresso();
                    break;
                case 2:
                    latte();
                    break;
                case 3:
                    cappuccino();
                    break;
                default:
                    System.out.println("not have the choice");
                    break;
            }
        }


    }


    private static void espresso(){
        int[] espresso = {250,0,16};
        if(compare(espresso)) {
            setWater(-250);
            setBeans(-16);
            setCups(-1);
            setMoney(4);
        }

    }
    private static void latte(){
        int[] latte = {350,75,20};
        if(compare(latte)) {
            setWater(-350);
            setMilk(-75);
            setBeans(-20);
            setCups(-1);
            setMoney(7);
        }
    }
    private static void cappuccino(){
        int[] cappuccino = {200,100,12};
        if(compare(cappuccino)) {
            setWater(-200);
            setMilk(-100);
            setBeans(-12);
            setCups(-1);
            setMoney(6);
        }
    }
    private static boolean compare(int[] stuff){
        if(Data.water - stuff[0] < 0){
            System.out.println("Sorry, not enough water!");
            return false;
        }else if(Data.milk - stuff[1] < 0){
            System.out.println("Sorry, not enough milk!");
            return false;
        }else if(Data.beans-stuff[2] < 0 ){
            System.out.println("Sorry, not enough beans of coffee!");
            return false;
        }
        System.out.println("I have enough resources, making you a coffee!");
        return true;

    }


    static void setWater(int x) {
        water += x;
    }

    static void setMilk(int x) {
        milk += x;
    }

    static void setBeans(int x) {
        beans += x;
    }

    private static void setMoney(int x) {
        money += x;
    }

    static void fillMoney() {
        System.out.println("I gave you $"+money);
        money = 0;
    }

    static void setCups(int x) {
        cups += x;
    }
}




class Fill{

    static void process() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add: ");
        Data.setWater(scanner.nextInt());
        System.out.println("Write how many ml of milk do you want to add: ");
        Data.setMilk(scanner.nextInt());
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        Data.setBeans(scanner.nextInt());
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        Data.setCups(scanner.nextInt());
    }


}


class Action{
    static boolean process(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write action (buy, fill, take, remaining, exit): ");

        String string = scanner.next();

        switch (string){
            case "buy":
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                Data.buyCoffee(scanner.next());
                break;
            case "fill":
                Fill.process();
                break;
            case "take":
                Data.fillMoney();
                break;
            case "remaining":
                Data.printAllData();
                break;
            case"exit":
                return false;
            //break;
            default:
                System.out.println("Not have a choice!");
                break;
        }
        return true;
    }
}




