package polygonstuff;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PolyInfoStage extends Stage {
	
	Polygonfile Polygonfile = new Polygonfile();
	
	PolyInfoStage()
	{
		// screw this
	}
	
	PolyInfoStage (int index)
	{
		String[] arrpoints = new String [15];
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		GridPane pane = new GridPane();															
		pane.setStyle("-fx-background-color: honeydew;");	
		
		Label lblname = new Label("Name");
		Label lblarea = new Label("Area");
		Label lblperimeter = new Label("Perimeter");
		Label lblpoints = new Label("Points");
		
		Label lblrealname = new Label();
		Label lblrealarea = new Label();
		Label lblrealperimeter = new Label();
		Label[] lblrealpoints = new Label[15];
		
		lblrealname.setText(Polygonfile.getPolygon(index).toString());
		lblrealarea.setText(df.format(Polygonfile.getArea(index)));
		lblrealperimeter.setText(df.format(Polygonfile.getPerimeter(index)));
		
		pane.add(lblname, 0, 0);
		pane.add(lblarea, 0, 1);
		pane.add(lblperimeter, 0, 2);
		pane.add(lblpoints, 0, 4);
		
		pane.add(lblrealname, 1, 0);
		pane.add(lblrealarea, 1, 1);
		pane.add(lblrealperimeter, 1, 2);
		
		arrpoints = Polygonfile.getPolygon(index).getArrPoints();
		
		for (int i = 0; i < (Polygonfile.getPolygon(index).getSize() + 1); i++)
		{
			lblrealpoints[i] = new Label();
			lblrealpoints[i].setText(arrpoints[i]);
			pane.add(lblrealpoints[i], 1, (4 + i));
		}
		
		pane.setPadding(new Insets(10, 15, 10, 15));													
		pane.setHgap(12);																			
		pane.setVgap(7);
		
		Scene scene = new Scene(pane, pane.getMinHeight(), pane.getMinWidth());							
		
		this.setTitle("");															
		
		this.setResizable(false);															
		this.setScene(scene);																
		this.show();																		
		
		this.centerOnScreen();																	
		this.setY((this.getY() * 3f) / 2f);		
	}
	
}
