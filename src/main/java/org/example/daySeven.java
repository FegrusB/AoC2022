package org.example;

import java.io.File;
import java.util.*;

public class daySeven {


    public static void main(String[] args) {

        Scanner myScanner = GetScanner.get("Day-7");
        ArrayList<Directory> directories = new ArrayList<>();

        Directory workingDir = new Directory("/", new Directory(""));
        directories.add(workingDir);
        myScanner.nextLine();

        while (myScanner.hasNext()){
            String line = myScanner.nextLine();
            ArrayList<String> splitLine = new ArrayList<>(Arrays.asList(line.split(" ")));
            if(line.charAt(0) == '$'){
                if (line.charAt(2) == 'c') {
                    if (splitLine.get(2).equals("..")) {
                        workingDir = workingDir.getParent();
                    } else {
                        workingDir = workingDir.getSubDirectory(splitLine.get(2));
                    }
                }
            }else if(line.charAt(0) == 'd'){
                Directory newDir = new Directory(splitLine.get(1),workingDir);
                workingDir.addDir(newDir);
                directories.add(newDir);
            }else {
                directories.get(directories.indexOf(workingDir)).addFile(new FileDay7(Long.parseLong(splitLine.get(0)),splitLine.get(1)));
            }
        }

        directories.get(0).getTotalSize();
        long sum = 0;
        for (Directory dir: directories){
            if (dir.totalSize < 100000){
                sum += dir.totalSize;
            }
        }

        System.out.println(sum);

    }

}

class Directory{

    ArrayList<Directory> subDirectories;
    ArrayList<FileDay7> files;
    String name;

    Directory parent;

    long totalSize;



    public Directory(String name){this.name = name;}
    public Directory(String name,Directory parent) {
        this.name = name;
        subDirectories = new ArrayList<>();
        files = new ArrayList<>();
        this.parent = parent;
        totalSize = 0;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != Directory.class){return false;}
        else{
            Directory dirO = (Directory) o;
            return dirO.getName().equals(this.getName());
        }
    }

    public long getTotalSize(){
        for (Directory dir: subDirectories){totalSize += dir.getTotalSize();}
        for (FileDay7 file: files) {totalSize += file.size;}
        return totalSize;
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public Directory getSubDirectory(String name) {
        return subDirectories.get(subDirectories.indexOf(new Directory(name)));
    }

    public Directory getParent() {
        return parent;
    }

    public ArrayList<FileDay7> getFiles() {
        return files;
    }

    public String getName() {
        return name;
    }

    public void addFile(FileDay7 fileDay7){files.add(fileDay7);}
    public void addDir(Directory dir){subDirectories.add(dir);}
}
class FileDay7{
    String name;
    long size;

    public FileDay7(long size, String name) {
        this.name = name;
        this.size = size;
    }
}
