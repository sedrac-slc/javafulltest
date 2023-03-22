package gest.file;

public sealed class StorageFile permits PersonFile {
    protected static final String FOLDER_STORAGE_DEFAULT;
    static {
        FOLDER_STORAGE_DEFAULT = System.getProperty("user.dir") + "/storage";
    }

}