import java.util.concurrent.locks.*;

public class PrintAlphabet {
		
	Lock l =new ReentrantLock() ;
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Run r =new Run();
		
		FirstThread f=new FirstThread(r);
		SecondThread s =new SecondThread(r);
		ThirdThread t= new ThirdThread(r);
		FourthThread fo=new FourthThread(r);
		
		Thread t1=new Thread(f);
		Thread t2=new Thread(s);
		Thread t3=new Thread(t);
		Thread t4=new Thread(fo);
		
		
		t1.start();
		Thread.sleep(100);
		t2.start();
		Thread.sleep(100);
		t3.start();
		Thread.sleep(100);
		t4.start();
		
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		
		System.out.println("***Finish***");
		
		
	}


}

//learn these from class notes 
class FirstThread implements Runnable{
	Run r;
	
	public FirstThread(Run r) {
		this.r=r;
	}
	
	public void run() {
		try {
			r.firstThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class SecondThread implements Runnable{
	Run r;
	
	public SecondThread (Run r) {
		this.r=r;
	}
	
	public void run() {
		try {
			r.secondThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThirdThread implements Runnable{
	Run r;
	
	public ThirdThread(Run r) {
		this.r=r;
	}
	
	public void run() {
		try {
			r.thirdThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class FourthThread implements Runnable{
	Run r;
	
	public FourthThread(Run r) {
		this.r=r;
	}
	
	public void run(){
		try {
			r.fourthThread();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Run {
	private char c ='A';
	Lock l = new ReentrantLock();
	Condition t1 = l.newCondition();	
	Condition t2 = l.newCondition();
	Condition t3 = l.newCondition();
	Condition t4 = l.newCondition();
	
	public void firstThread() throws InterruptedException {
		l.lock();
		
		while(c<='Z') {
			System.out.println("Thread 1 "+c++);
			t2.signal();
			t1.await();
		}
		t2.signal();
		l.unlock();
	}
	
	public void secondThread() throws InterruptedException {
		l.lock();		
		while(c<='Z') {
			System.out.println("Thread 2 "+c++);
			
			t3.signal();
			t2.await();
		}
		t3.signal();
		l.unlock();

	}
	
	public void thirdThread() throws InterruptedException {
		l.lock();
	
		while(c<='Z') {
			System.out.println("Thread 3 "+c++);
			t4.signal();
			t3.await();
		}
		t4.signal();
		l.unlock();

	}
	
	public void fourthThread() throws InterruptedException {
		l.lock();
		
		while(c<='Z') {
			System.out.println("Thread 4 "+c++);
			t1.signal();
			t4.await();
		}
		t1.signal();
		l.unlock();

	}
}
