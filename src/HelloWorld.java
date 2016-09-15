
import Camera.DynamicCamera;
import GameObjects.DynamicBody;
import Input.KeyboardHandler;
import WorldGen.World;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public class HelloWorld {

    // The window handle
    private long window;
    
    int fps;
    long lastMilliseconds;
    
    private GLFWKeyCallback keyCallback;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        try {
            init();
            loop();

            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Setup variables for use in fps calcualtion
        lastMilliseconds = getTime();

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        int WIDTH = 800;
        int HEIGHT = 600;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Agraletha: Open Seas", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
            }
        });
        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());   
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }
    double x=0;
    DynamicCamera camera;
    DynamicBody body;
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.2f, 0.3f, 0.7f, 0.0f);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        World myWorld=new World(32, 128);
        camera=new DynamicCamera(myWorld);
        body=new DynamicBody("0,0",0,0);
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            // Top & Red
            update();
            camera.render();
            body.render(0,0);
            // Poll for window events. The key callback above will only be
            
            glfwSwapBuffers(window); // swap the color buffers
            // invoked during this call.
            glfwPollEvents();
        }
    }
    
    private void update(){
        
        //updateFPS();
        
        if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT))
            camera.moveCamera(10, 0);
        if(KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT))
            camera.moveCamera(-10, 0);
        if(KeyboardHandler.isKeyDown(GLFW_KEY_UP))
            camera.moveCamera(0, -10);
        if(KeyboardHandler.isKeyDown(GLFW_KEY_DOWN))
            camera.moveCamera(0, 10);
        //camera.moveCamera(-4, -4);
    }
    
    public void updateFPS() {
        if (getTime() - lastMilliseconds > 1000) {
            //Display.setTitle("FPS: " + fps);
            System.out.println(fps);
            fps = 0;
            lastMilliseconds += 1000;
        }
        fps++;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public static void main(String[] args) {
        new HelloWorld().run();
    }

}