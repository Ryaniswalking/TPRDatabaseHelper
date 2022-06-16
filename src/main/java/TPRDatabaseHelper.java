import org.walker.tprDBHelper.controllers.DatabaseInfoController;
import org.walker.tprDBHelper.models.CouchDBModel;
import org.walker.tprDBHelper.views.DatabaseInfoForm;
import org.walker.tprDBHelper.views.TPRDatabaseHelperView;
import org.walker.tprDataConverter.controllers.FileInputController;
import org.walker.tprDataConverter.models.FileInputModel;
import org.walker.tprDataConverter.views.FileInputView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TPRDatabaseHelper {
    public static void main(String[] args){
//        new DBInfoFormController(new DBInfoFormView());
//        DatabaseInfoForm dbForm = new DatabaseInfoForm();
//        CouchDBModel dbModel = new CouchDBModel();
//        new DatabaseInfoController(dbForm, dbModel);
//
//        dbForm.setVisible(true);
        new TPRDatabaseHelperView();
    }
}
