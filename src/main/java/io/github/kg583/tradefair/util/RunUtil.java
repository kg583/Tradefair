package io.github.kg583.tradefair.util;

public abstract class RunUtil {
    public static void runSafe(Runnable toRun) {
        try {
            toRun.run();
        } catch (RuntimeException ignored) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
