package tensor;
import org.tensorflow.lite.Interpreter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class TFLiteLoader {

    private static Context mContext;
    Interpreter mInterpreter;
    private static TFLiteLoader instance;

    public static TFLiteLoader newInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new TFLiteLoader();
        }
        return instance;
    }


    public Interpreter get() {
        try {
            if (Objects.isNull(mInterpreter))
                mInterpreter = new Interpreter(loadModelFile(mContext));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mInterpreter;
    }


    // 获取文件
    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd("cnn.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
