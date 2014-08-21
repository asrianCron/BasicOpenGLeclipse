package com.model;

public class Ticker extends Thread{
	long currentTime;
	long lastTick;
	long interval;
	long sleepInterval;
	long lastPlusInterval;
	boolean running;
	public Ticker(long interval){
		super();
		currentTime = System.currentTimeMillis();
		lastTick = 0l;
		this.interval = interval;
		sleepInterval = 100l;
		running = false;
	}
	
	@Override
	public void run(){
		running = true;
		sleepInterval = interval/10;
		lastTick = System.currentTimeMillis();
		while(true){
			currentTime = System.currentTimeMillis();
			lastPlusInterval = lastTick + interval;
			if(currentTime > (lastPlusInterval)){
				doAction();
				lastTick = System.currentTimeMillis();
				sleepInterval = interval /10;
			}else if(currentTime > (lastPlusInterval - interval * 1/4)){
				sleepInterval = sleepInterval / 200;
//				System.out.println("else");
			}
			try {
				sleep(sleepInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void updateTick(){
		currentTime = System.currentTimeMillis();
	}
	public void doAction(){
//		Game.tickerAction();
	}
}
