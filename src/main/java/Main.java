import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;

public class Main implements NativeKeyListener {

    private boolean isRun = false;
    private Thread scriptThread;
    private final Robot robot;

    public Main(Robot robot) {
        this.robot = robot;
    }

//    public void nativeKeyReleased(NativeKeyEvent e) {
//
//        if (e.getKeyCode() == NativeKeyEvent.VC_Z) {
//            this.isRun = false;
//            this.scriptThread = null;
//            System.out.println("Script is stopping!");
//        }
//    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_Z && !this.isRun) {
            this.isRun = true;
            if (this.scriptThread == null) {
                this.script();
            }
        } else {
            this.isRun = false;
            this.scriptThread = null;
        }
    }

    public void script() {

        System.out.println("Script is running!");
        this.scriptThread = new Thread(() -> {
            while (this.isRun) {

                this.robot.mouseWheel(-1);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Script is stopping!");
        });

        this.scriptThread.start();
    }

    public static void main(String[] args) throws NativeHookException, AWTException {
        GlobalScreen.registerNativeHook();

        Main main = new Main(new Robot());

        GlobalScreen.addNativeKeyListener(main);
    }
}
