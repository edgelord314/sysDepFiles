package de.edgelord.systemdependentfiles;

import java.io.File;

/**
 * This class contains a couple of methods for getting Files or paths
 * depending on the current user and the Operating System.
 * <p>
 * Tested and working on: <code>macOS</code>
 * <p>
 * TODO: Windows stuff
 */
public class SystemDependentFiles {

    public enum OS {
        macOS,
        linux,
        windows
    }

    private SystemDependentFiles() {
    }

    private static OS os = null;
    private static String user = null;

    private static final String macOSSearchPhrase = "mac";
    private static final String linuxSearchPhrase = "linux";
    private static final String windowsSearchPhrase = "windows";
    private static final String macOSUserDirPrefix = "/Users/";
    private static final String linuxUserDirPrefix = "/home/";
    private static final String windowsUserDir = System.getProperty("user.home");
    private static final String windowsSystemPath = "C:/";

    /**
     * This method returns the current user's home directory
     * as a File object depending on the Operating System. For example,
     * on linux, with "joe" as the current user, this method would return a
     * <code>File</code> with the absolute path <code>/home/joe/</code>.
     *
     * @return the home directory of the current user as a <code>File</code>.
     */
    public static File getUserDir() {

        return new File(getUserDirPath());
    }

    /**
     * This method returns the path to the absolute path of the
     * home directory of the current user depending on the Operating System.
     * For example, on macOS, with the current user being "joe", this method
     * would return <code>"/Users/joe/"</code>.
     *
     * @return the absolute path of the home directory of the current user.
     */
    public static String getUserDirPath() {

        checkOS();
        checkUser();

        switch (os) {

            case macOS:
                return macOSUserDirPrefix + user + "/";
            case linux:
                return linuxUserDirPrefix + user + "/";
            case windows:
                return windowsUserDir + "/";
        }

        return "lorem/ipsum";
    }

    /**
     * This method returns the <code>File</code> that belongs to the given relative path
     * from the current user's home directory.
     * For example, if the current user is "joe", the OS is linux and the
     * relative path is "lorem/ipsum.foo", this would return a <code>File</code>
     * with the absolute path "/home/joe/lorem/ipsum.foo".
     *
     * @param relativePath the path of the <code>File</code> relative to the user directory
     * @return the <code>File</code> from the user's directory that belongs to the given relative path.
     */
    public static File getUserFile(String relativePath) {
        return new File(getUserDirPath() + relativePath);
    }

    /**
     * This method return the <code>File</code> from the directory below the user's directory
     * that belongs to the given relative path.
     * For example: If the OS is linux or macOS and the relative path is "usr/share", this method
     * would return a file with the absolute path <code>"/usr/share"</code> (notice the "/" in front
     * which wouldn't be correct on a windows system).
     *
     * @param relativePath the path relative to the directory below the user's directory.
     * @return the file from the directory below the user's directory that belongs to the given
     * relative path
     */
    public static File getSystemFile(String relativePath) {

        checkOS();

        switch (os) {

            case macOS:
                return new File("/" + relativePath);
            case linux:
                return new File("/" + relativePath);
            case windows:
                return new File(windowsSystemPath + relativePath);
        }

        return new File("/lorem/ipsum");
    }

    /**
     * Checks for the os and then returns its type.
     * On a Windows computer for example, this method would check the os
     * and would most likely return <code>OS.windows</code>.
     *
     * @return the operating system of the computer that is running this
     */
    public static OS getOs() {
        checkOS();

        return os;
    }

    private static void checkOS() {

        if (os == null) {

            if (System.getProperty("os.name").toLowerCase().contains(macOSSearchPhrase)) {
                os = OS.macOS;
            } else if (System.getProperty("os.name").toLowerCase().contains(linuxSearchPhrase)) {
                os = OS.linux;
            } else if (System.getProperty("os.name").toLowerCase().contains(windowsSearchPhrase)) {
                os = OS.windows;
            } else {
                // If the os is none of the three, it is most likely somehting like openSuse or anything like that
                // for which linux is probably the most similar
                os = OS.linux;
            }
        }
    }

    private static void checkUser() {

        if (user == null) {
            user = System.getProperty("user.name");
        }
    }
}
