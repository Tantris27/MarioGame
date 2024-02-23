package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    // private static int currentSceneIndex=-1;
    private static Scene currentScene;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }
    public float r=1.0f,g=1.0f,b=1.0f,a=1.0f;
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();

        }
        return Window.window;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene= new LevelScene();
                currentScene.init();
                break;
            default:
                assert false: "Unknown Scene '"+ newScene +"'";
                break;
        }
    }

    public void run() {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");
        init();
        loop();

        //Free the Memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and free the Error Callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        //Set up ErrorCallback
        GLFWErrorCallback.createPrint(System.err).set();
        // init GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        //Create Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW Window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable V-sync
        glfwSwapInterval(1);
        // Make window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime ;
        float dt = -1.0f;
        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll Events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);
            if(dt>=0.0f){
                currentScene.update(dt);
            }
          /*  if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space");
            }*/
            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
