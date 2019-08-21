package org.pytorch;

import com.facebook.jni.HybridData;

import java.nio.Buffer;

public class Module {

  private NativePeer mNativePeer;

  public static Module load(final String modelAbsolutePath) {
    return new Module(modelAbsolutePath);
  }

  private Module(final String modelAbsolutePath) {
    this.mNativePeer = new NativePeer(modelAbsolutePath);
  }

  public IValue forward(IValue... inputs) {
    return mNativePeer.forward(inputs);
  }

  private static class NativePeer {
    static {
      System.loadLibrary("pytorch");
    }

    private final HybridData mHybridData;

    private static native HybridData initHybrid(String moduleAbsolutePath);

    NativePeer(String moduleAbsolutePath) {
      mHybridData = initHybrid(moduleAbsolutePath);
    }

    private native IValue forward(IValue... inputs);
  }
}
