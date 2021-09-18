/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package myDemoApp;


import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }
        
    public static int[] sortTheArrayBetweenIndexes(int[] array,int firstIndex,int secondIndex,boolean decreasedOrIncrease){//true:decrease,false:increase
        if(firstIndex>secondIndex){
            int temp=firstIndex;
            firstIndex=secondIndex;
            secondIndex=temp; 
        }
        if(firstIndex<0){
            firstIndex=0;
        }
        if(secondIndex>array.length-1){
            secondIndex=array.length-1;
        }
        int key;
        for(int i=firstIndex+1;i<=secondIndex;i++){
            key=array[i];
            int j=i-1;
            if(!decreasedOrIncrease){
                while(j>=firstIndex && array[j]>key){
                    array[j+1]=array[j];
                    j--;
                }
            }
            else{
                while(j>=firstIndex && array[j]<key){
                    array[j+1]=array[j];
                    j--;
                }
            }
            array[j+1]=key;
        } 
        return array;
    }  
    
    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {

          String input1 = req.queryParams("input1");
          java.util.Scanner sc1 = new java.util.Scanner(input1);
          sc1.useDelimiter("[;]+");

          java.util.ArrayList<Integer> inputArrayList = new java.util.ArrayList<>();
          while(sc1.hasNext()){
            int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
            inputArrayList.add(value);
          }
          sc1.close();
          int[] inputArray = new int[inputArrayList.size()];
          for(int i=0;i<inputArrayList.size();i++){
              inputArray[i] = inputArrayList.get(i);
          }

          String input2 = req.queryParams("input2");
          java.util.Scanner sc2 = new java.util.Scanner(input2);
          sc2.useDelimiter("[\n]+");
          int firstIndex = Integer.parseInt(sc2.next().replaceAll("\\s",""));
          sc2.close();

          String input3 = req.queryParams("input3");
          java.util.Scanner sc3 = new java.util.Scanner(input3);
          sc3.useDelimiter("[\n]+");
          int secondIndex = Integer.parseInt(sc3.next().replaceAll("\\s",""));
          sc3.close();

          String input4 = req.queryParams("input4");
          java.util.Scanner sc4 = new java.util.Scanner(input4);
          sc4.useDelimiter("[\n]+");
          String decreasedOrIncreaseString = sc4.next().replaceAll("\\s","");
          sc4.close();

          boolean decreaseOrIncrease;
          if(decreasedOrIncreaseString.toUpperCase().equals("TRUE")){
              decreaseOrIncrease = true;
          }
          else{
              decreaseOrIncrease = false;
          }

          int[] resultArray = App.sortTheArrayBetweenIndexes(inputArray, firstIndex, secondIndex, decreaseOrIncrease);
          String resultString ="";
          for(int i=0;i<resultArray.length-1;i++){
              resultString = resultString+Integer.toString(resultArray[i])+";";
          }
          resultString = resultString+Integer.toString(resultArray[resultArray.length-1]);

          Map<String, String> map = new HashMap<String, String>();
          map.put("result", "\""+resultString+"\"");
          return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "not computed yet!");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
