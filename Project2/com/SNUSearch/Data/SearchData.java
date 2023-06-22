package com.SNUSearch.Data;

import java.io.File;
import java.util.ArrayList;

interface SearchData{
    public abstract void saveData(String id, String line);
    public abstract String loadData(String id);
    public abstract String loadHot();
}
