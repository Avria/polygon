package polygonstuff;

import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Group;

import javafx.scene.input.MouseEvent;

public class PolygonGUI extends Application {
		
		int numPoints = 0, userx, usery, numcolors = 0, flag;
		final int height = 600, width = 600, totalcolors = 5;
		
		static ArrayList<Point> arrbegin = new ArrayList<Point>();
		static ArrayList<Point> arrend = new ArrayList<Point>();
		
		Color[] arrcolors = {Color.LIGHTPINK, Color.CORNFLOWERBLUE, Color.LIGHTGREEN, Color.KHAKI, Color.THISTLE};
		String[] arrcolornames = {"Light Pink", "Cornflower Blue", "Light Green", "Khaki", "Thistle"};
	
		private static Label lblpos = new Label("");												
		private static Button btDelete = new Button("Delete");
		private static Button btFinish = new Button("Finish");
		
		private static Polygonfile Polygonfile = new Polygonfile();
		
		static ListView<String> lstpol = new ListView<>();	
		static ObservableList<String> olstpol = FXCollections.observableArrayList();
		
		Canvas canvas = new Canvas(height, width);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Group root = new Group();
		
		@Override
		public void start(Stage primaryStage) 
		{
	        drawShapes(gc);
	        
	        Polygonfile.open();
	        olstpol.setAll(Polygonfile.listAllItems());															
			lstpol.setItems(olstpol);
			
			flag = 4;
			
			for (int counter = 0; counter < Polygonfile.getSize(); counter++)
			{
				for (int i = 0; i < Polygonfile.getNumPoints(counter); i++)
				{
					arrbegin.add(Polygonfile.getPolygon(counter).getRealPointP1(i));
					arrend.add(Polygonfile.getPolygon(counter).getRealPointP2(i));
				}
				
				numcolors = Polygonfile.getPolygon(counter).getColornum();
				drawShapes(gc);
			}
			
			if (Polygonfile.getSize() > 0)
				numcolors = Polygonfile.getPolygon(Polygonfile.getSize() - 1).getColornum() + 1;
			
			if (numcolors == totalcolors)
				numcolors = 0;
	        
	        root.getChildren().add(canvas);
	        primaryStage.setScene(new Scene(root));
			
			root.getChildren().add(lblpos);
			root.getChildren().add(lstpol);
			root.getChildren().add(btFinish);
			root.getChildren().add(btDelete);
			
			lblpos.setStyle("-fx-font: 16 calibri;");
			lblpos.setLayoutX(745);
			lblpos.setLayoutY(50);
			
			lstpol.setMaxWidth(180);
			lstpol.setPrefHeight(250);
			lstpol.setLayoutX(690);
			lstpol.setLayoutY(100);
			
			btFinish.setLayoutX(690);
			btFinish.setLayoutY(370);
			btFinish.setPrefWidth(70);
			
			btDelete.setPrefWidth(70);
			btDelete.setLayoutX(800);
			btDelete.setLayoutY(370);
			
			primaryStage.setTitle("Polygon");
			primaryStage.setResizable(false);
			
			primaryStage.show();																	
			primaryStage.centerOnScreen();
			
			primaryStage.setHeight(height);
			primaryStage.setWidth(width + 350);
			primaryStage.setY((primaryStage.getY() * 3f) / 2f);	
			
			lstpol.setOnMouseClicked(new EventHandler<MouseEvent>()	
			{
			    public void handle(MouseEvent mouseEvent) 
			    {
		            if(mouseEvent.getClickCount() == 2)
		            {
		                new PolyInfoStage(lstpol.getSelectionModel().getSelectedIndex());
		            }
			    }
			});
			
			canvas.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() 
 			{
		           @Override
		           public void handle(MouseEvent e) 
		           {
		        	    int currentx = (int) e.getX();
		        	    int currenty = (int) e.getY();
		            	
		        	    if (currentx > (width / 2))
				    	{
		            		userx = currentx - (width/2);
				    	}
		            	else if (currentx < (width / 2))
		            	{
		            		userx = (width / 2 - currentx) * -1;
		            	}
		            	else if (currentx == (width / 2))
		            	{
		            		userx = 0;
		            	}
		            	if ( (currenty < (height / 2)) || (currenty > (height) / 2) )
		            	{
		            		usery =  (currenty - (height / 2)) * -1;
		            	}
		            	else if (currenty == (height / 2))
		            	{
		            		usery = 0;
		            	}
		            	
		            	lblpos.setText("(" + userx + ", " + usery + ")");
		           }
		           
	 			});

			canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() 
	 			{
					@Override
					public void handle(MouseEvent e) 
					{
						if (numPoints == 0)
						{
							Point p1 = new Point((int)e.getX(), (int)e.getY());
							p1.drawPoint(gc);
						
							arrbegin.add(p1);
						}
						else
						{
							Point p1 = new Point((int)e.getX(), (int)e.getY());
							p1.drawPoint(gc);
							
							arrbegin.add(p1);
							arrend.add(p1);
						}
						
						flag = 1;
						drawShapes(gc);
					}
		           
	 			});
			
			primaryStage.setOnCloseRequest(e -> Polygonfile.save());
											
			btFinish.setOnAction(e -> finish());
			btDelete.setOnAction(e -> delete(lstpol.getSelectionModel().getSelectedIndex()));
			
		}
		
		private void delete(int value)
		{
			flag = 3;
			
			Polygonfile.delete(value);
	      	olstpol.setAll(Polygonfile.listAllItems());													
	      	lstpol.setItems(olstpol);
			
			drawShapes(gc);
		}
		
		private void finish()
		{
			arrend.add(arrbegin.get(0));
			numPoints = 0;
			
			flag = 2;
			
			Polygonfile.getPolygon(Polygonfile.getSize() - 1).setColor(arrcolornames[numcolors]); 
			Polygonfile.getPolygon(Polygonfile.getSize() - 1).setColornum(numcolors); 
			
			olstpol.setAll(Polygonfile.listAllItems());
			lstpol.setItems(olstpol);
			
			//Polygonfile.addNewItem(arrbegin, arrend, arrcolornames[numcolors], numcolors);
		   	//olstpol.setAll(Polygonfile.listAllItems());													
		   	//lstpol.setItems(olstpol);
			
			drawShapes(gc);
			
		}
		
		private void drawShapes(GraphicsContext gc) 
		 {
			// flag = 0 means draw axes and tick marks
			// 1 means draw points and line segments
			// 2 means finish the polygon, clear begin and end array and redraw points over line segments
			// 3 means delete? remember to set line width to 1 and color to dark slate gray!
			// 4 means draw shapes from textfile! =)
	        
			if (flag == 0)
			{
				gc.setStroke(Color.DARKSLATEGRAY); 
				
		        int posy, posx;
		        
		        gc.strokeLine(0, height/2, width, height/2);
		        gc.strokeLine(width/2, 0, width/2, height);
				
				posy = height;
				posx = width;
				
				for (int i = 0; i < 20; i++)
				{
					if (i != 10)
					{
						gc.strokeLine(width/2 - 5, posy, width/2 + 5, posy);
						gc.strokeLine(posx, height/2 - 5, posx, height/2 + 5); 			
					}
					
					posy = Math.round(posy - height/20);
					posx = Math.round(posx - width/20);
				}
				
				gc.strokeLine(width, 0, width, height);
				
			}
			else if (flag == 1)
			{
				if (numPoints == 0)
				{
					gc.setFill(arrcolors[numcolors]);
					gc.setStroke(arrcolors[numcolors]);
					
					gc.fillOval(arrbegin.get(0).getX() - 5, arrbegin.get(0).getY() - 5, 10, 10);
					Polygonfile.newPolygon();
					
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).setColor(arrcolornames[numcolors]);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).setColornum(numcolors);
					
					numPoints++;
				}
				else if (numPoints == 1)
				{
					gc.setLineWidth(3);
					
					gc.setFill(arrcolors[numcolors]);
					gc.setStroke(arrcolors[numcolors]);
					
					Lineseg l = new Lineseg(arrbegin.get(0), arrend.get(0));
					Polygonfile.addNewLineseg(Polygonfile.getSize() - 1, l);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).getLineseg(numPoints - 1).setLabelP1(numPoints);
					
					gc.fillOval(arrbegin.get(1).getX() - 5, arrbegin.get(1).getY() - 5, 10, 10);
					gc.strokeLine(arrbegin.get(0).getX(), arrbegin.get(0).getY(), arrend.get(0).getX(), arrend.get(0).getY());
					
					numPoints++;
				}
				else if (numPoints == 2) 				// triangle
				{
					//int i = arrbegin.size() - 2;
					//int n = arrend.size() - 1;
					
					gc.setFill(arrcolors[numcolors]);
					gc.setStroke(arrcolors[numcolors]);
				
					gc.strokeLine(arrbegin.get(1).getX(), arrbegin.get(1).getY(), arrend.get(1).getX(), arrend.get(1).getY());
					gc.fillOval(arrbegin.get(2).getX() - 5, arrbegin.get(2).getY() - 5, 10, 10);
					
					Lineseg l = new Lineseg(arrbegin.get(1), arrend.get(1));
					Polygonfile.addNewLineseg(Polygonfile.getSize() - 1, l);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).getLineseg(numPoints - 1).setLabelP1(numPoints);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).getLineseg(numPoints - 1).setLabelP2(numPoints + 1);
					
					gc.strokeLine(arrbegin.get(0).getX(), arrbegin.get(0).getY(), arrbegin.get(2).getX(), arrbegin.get(2).getY());
					
					numPoints++;
				}
				else if (numPoints > 2)
				{
					// use delete code to wipe the canvas
					// then use drawPolygon and (edit code in Polygon to account for last line)
					// remember to fix the eventhandler code for this one too
					
					root.getChildren().clear();
					
					root.getChildren().add(canvas);
					root.getChildren().add(lblpos);
					root.getChildren().add(lstpol);
					root.getChildren().add(btDelete);
					root.getChildren().add(btFinish);
					
					lblpos.setStyle("-fx-font: 16 calibri;");
					lblpos.setLayoutX(745);
					lblpos.setLayoutY(50);
					
					lstpol.setMaxWidth(180);
					lstpol.setPrefHeight(250);
					lstpol.setLayoutX(690);
					lstpol.setLayoutY(100);
					
					btDelete.setPrefWidth(70);
					btDelete.setLayoutX(800);
					btDelete.setLayoutY(370);
					
					btFinish.setLayoutX(690);
					btFinish.setLayoutY(370);
					btFinish.setPrefWidth(70);
					
					gc.clearRect(0, 0, 600, 600);
					
					gc.setLineWidth(1);
					gc.setStroke(Color.DARKSLATEGRAY);
					
					int posy, posx;
			        
			        gc.strokeLine(0, height/2, width, height/2);
			        gc.strokeLine(width/2, 0, width/2, height);
					
					posy = height;
					posx = width;
					
					for (int i = 0; i < 20; i++)
					{
						if (i != 10)
						{
							gc.strokeLine(width/2 - 5, posy, width/2 + 5, posy);
							gc.strokeLine(posx, height/2 - 5, posx, height/2 + 5); 			
						}
						
						posy = Math.round(posy - height/20);
						posx = Math.round(posx - width/20);
					}
					
					gc.strokeLine(width, 0, width, height);
					
					// finally doing some stuff ughugghghguhguhg
					
					int i = arrbegin.size() - 2;
					
					gc.setFill(arrcolors[numcolors]);
					gc.setStroke(arrcolors[numcolors]);
					gc.setLineWidth(3);
					
					Lineseg l = new Lineseg(arrbegin.get(i), arrend.get(i));
					
					Polygonfile.addNewLineseg(Polygonfile.getSize() - 1, l);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).getLineseg(numPoints - 1).setLabelP1(numPoints);
					Polygonfile.getPolygon(Polygonfile.getSize() - 1).getLineseg(numPoints - 1).setLabelP2(numPoints + 1);
					
					for (int j = 0; j < Polygonfile.getSize(); j++)
					{
						gc.setFill(arrcolors[Polygonfile.getPolygon(j).getColornum()]);
						gc.setStroke(arrcolors[Polygonfile.getPolygon(j).getColornum()]);
							
						Polygonfile.getPolygon(j).drawPolygon(gc);
					}
					
					numPoints++;
				}
			}
			else if (flag == 2)
			{
				// for finishing the polygon
				
				//gc.setStroke(arrcolors[numcolors]);
				//gc.strokeLine(arrbegin.get(0).getX(), arrbegin.get(0).getY(), arrend.get(i).getX(), arrend.get(i).getY());
				
				arrbegin.clear();
				arrend.clear();
				
				numcolors++;
				
				if (numcolors == totalcolors)
					numcolors = 0;
			}
			else if (flag == 3)
			{
				root.getChildren().clear();
				
				root.getChildren().add(canvas);
				root.getChildren().add(lblpos);
				root.getChildren().add(lstpol);
				root.getChildren().add(btDelete);
				root.getChildren().add(btFinish);
				
				lblpos.setStyle("-fx-font: 16 calibri;");
				lblpos.setLayoutX(745);
				lblpos.setLayoutY(50);
				
				lstpol.setMaxWidth(180);
				lstpol.setPrefHeight(250);
				lstpol.setLayoutX(690);
				lstpol.setLayoutY(100);
				
				btDelete.setPrefWidth(70);
				btDelete.setLayoutX(800);
				btDelete.setLayoutY(370);
				
				btFinish.setLayoutX(690);
				btFinish.setLayoutY(370);
				btFinish.setPrefWidth(70);
				
				gc.clearRect(0, 0, 600, 600);
				
				gc.setLineWidth(1);
				gc.setStroke(Color.DARKSLATEGRAY);
				
				int posy, posx;
		        
		        gc.strokeLine(0, height/2, width, height/2);
		        gc.strokeLine(width/2, 0, width/2, height);
				
				posy = height;
				posx = width;
				
				for (int i = 0; i < 20; i++)
				{
					if (i != 10)
					{
						gc.strokeLine(width/2 - 5, posy, width/2 + 5, posy);
						gc.strokeLine(posx, height/2 - 5, posx, height/2 + 5); 			
					}
					
					posy = Math.round(posy - height/20);
					posx = Math.round(posx - width/20);
				}
				
				gc.strokeLine(width, 0, width, height);
			
				gc.setLineWidth(3);
				
				for (int i = 0; i < Polygonfile.getSize(); i++)
				{
					gc.setFill(arrcolors[Polygonfile.getPolygon(i).getColornum()]);
					gc.setStroke(arrcolors[Polygonfile.getPolygon(i).getColornum()]);
					
					Polygonfile.getPolygon(i).drawPolygon(gc);
				}
			}
			else if (flag == 4)						
			{
				// for drawing it from textfile
				// populate arrbegin and arrend and reuse the above code
				// clear it for every iteration of the for loop
				
				gc.setLineWidth(3);
				
				for (int i = 0; i < Polygonfile.getSize(); i++)
				{
					gc.setFill(arrcolors[Polygonfile.getPolygon(i).getColornum()]);
					gc.setStroke(arrcolors[Polygonfile.getPolygon(i).getColornum()]);
					
					Polygonfile.getPolygon(i).drawPolygon(gc);
				}
				
				numcolors++;
				
				if (numcolors == totalcolors)
					numcolors = 0;
				
				arrbegin.clear();
				arrend.clear();
			}	
		}
		
		public static void main(String[] args)
		{
			Application.launch(args);	
		}

}
