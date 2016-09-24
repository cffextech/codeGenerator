package engine.dao;

import model.DataModel;

import java.util.List;

/**
 * Created by lzy on 2016/9/24.
 */
public abstract class ModelSource {

    protected String address;
    public abstract List<DataModel> getModel();
}
