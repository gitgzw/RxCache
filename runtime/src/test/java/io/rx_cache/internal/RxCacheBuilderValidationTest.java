package io.rx_cache.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.security.InvalidParameterException;

/**
 * Created by daemontus on 02/09/16.
 */
public class RxCacheBuilderValidationTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test(expected = InvalidParameterException.class)
    public void Cache_Directory_Null() {
        new RxCache.Builder()
            .persistence(null, Jolyglot$.newInstance());
    }

    @Test(expected = InvalidParameterException.class)
    public void Jolyglot_Null() {
        new RxCache.Builder()
                .persistence(temporaryFolder.getRoot(), null);
    }

    @Test(expected = InvalidParameterException.class)
    public void Cache_Directory_Not_Exist() {
        File cacheDir = new File(temporaryFolder.getRoot(), "non_existent_folder");
        new RxCache.Builder()
                .persistence(cacheDir, Jolyglot$.newInstance());
    }

    @Test(expected = InvalidParameterException.class)
    public void Cache_Directory_Not_Writable() {
        File cacheDir = new File(temporaryFolder.getRoot(), "non_existent_folder");
        if (!cacheDir.mkdirs()) {
            throw new IllegalStateException("Cannot create temporary directory");
        }
        if (!cacheDir.setWritable(false, false)) {
            throw new IllegalStateException("Cannot modify permissions");
        }
        new RxCache.Builder()
                .persistence(cacheDir, Jolyglot$.newInstance());
    }
}