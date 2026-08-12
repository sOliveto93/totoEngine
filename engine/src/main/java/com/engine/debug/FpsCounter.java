package com.engine.debug;

public class FpsCounter {

    private int frames;
    private int fps;
    private long lasTime;
    private boolean updated;
    public FpsCounter(){
        lasTime = System.nanoTime();
    }

    public void update(){
        frames++;
         long currentTime= System.nanoTime();

         if(currentTime - lasTime >= 1_000_000_000L){
            fps = frames;
            frames=0;
            lasTime=currentTime;
            mostrarFps();
         }
    }

    public int getFps(){
        return fps;
    }
    public void mostrarFps(){
      
            System.out.println("FPS: " + fps);
       
        
        
    }


}
