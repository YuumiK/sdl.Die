package jp.ac.titech.itpro.sdl.die;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Stand implements Obj {
    private final static float[] VERTICES = {
            // left
            0,0,0,
            0,1,0,
            0,0,1,
            0,1,1,
            // right
            0,0,0,
            1,0,0,
            0,0,1,
            1,0,1,
            // bottom
            1,-0.1f,0,
            1,0,0,
            1,-0.1f,1,
            1,0,1,
            // top
            -0.1f,-0.1f,0,
            1,-0.1f,0,
            -0.1f,-0.1f,1,
            1,-0.1f,1,
            // back
            -0.1f,-0.1f,0,
            -0.1f,1,0,
            -0.1f,-0.1f,1,
            -0.1f,1,1,
            // front
            -0.1f,1,0,
            0,1,0,
            -0.1f,1,1,
            0,1,1,

            -0.1f,0,1,
            0,0,1,
            -0.1f,1,1,
            0,1,1,

            -0.1f,0,1,
            1,0,1,
            -0.1f,-0.1f,1,
            1,-0.1f,1,

            -0.1f,0,0,
            0,0,0,
            -0.1f,1,0,
            0,1,0,

            -0.1f,0,0,
            1,0,0,
            -0.1f,-0.1f,0,
            1,-0.1f,0,
    };

    private final FloatBuffer vbuf;

    Stand() {
        vbuf = ByteBuffer
                .allocateDirect(VERTICES.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vbuf.put(VERTICES);
        vbuf.position(0);
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vbuf);

        // left
        gl.glNormal3f(1, 0, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // right
        gl.glNormal3f(0, 1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);

        // bottom
        gl.glNormal3f(1, 0, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);

        // top
        gl.glNormal3f(0, -1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);

        // rear
        gl.glNormal3f(-1, 0, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);

        // front
        gl.glNormal3f(0, 1, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);

        gl.glNormal3f(0, 0, 1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 24, 4);

        gl.glNormal3f(0, 0, 1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 28, 4);

        gl.glNormal3f(0, 0, -1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 32, 4);

        gl.glNormal3f(0, 0, -1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 36, 4);
    }
}
