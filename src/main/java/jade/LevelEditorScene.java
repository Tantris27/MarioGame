package jade;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCreateShader;

public class LevelEditorScene extends Scene {

    private String vertexShaderSource = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSource = "#version 330 core\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "void main(){\n" +
            "    color=fColor;\n" +
            "}";

    private int vertexId, fragmentId, shaderProgram;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        //Compile and Link Shaders
        // First load and compile vertex shader
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        //pass shader sourcecode to the gpu
    }

    @Override
    public void update(float dt) {

    }

}
