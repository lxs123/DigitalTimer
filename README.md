# DigitalTimer
数字时钟 attrs 文件                                                                                                                   
<  resources  >                                                                                                                     
    < declare-styleable name="DigitalTimer ">                                                                                      
        < attr name="textColor" format="color "/>                                                                                  
        < attr name="textSize" format="dimension "/>                                                                                                 
        < attr name="textBgRes" format="reference "/>                                                                               
        < attr name="timeColonPadding" format="dimension "/>                                                                       
        < attr name="textBgPaddingLR" format="dimension "/>                                                                         
        < attr name="textBgPaddingTB" format="dimension "/>                                                                         
    </ declare-styleable>                                                                                                           
</ resources>                                                                                                                       

在 MainActivity 的代码：
         digitalTimer = (DigitalTimer) findViewById(R.id.dt);                                                                       
        TextView tv = (TextView) findViewById(R.id.tv);                                                                             
        tv.setOnClickListener(new View.OnClickListener() {                                                                          
            @Override                                                                                                               
            public void onClick(View view) {                                                                                                            
            //输入的是当前时间，需要转换成毫秒的时间戳                                                                                                      
                digitalTimer.setBaseTime("2017-07-08 13:13:42");                                                                            
                digitalTimer.start();                                                                                                           
            }                                                                                                                       
        });                                                                                                                                                                                                                                                         
 xml 代码：                                                                                                                                                    
  <com.toocms.freeman.digitaltimer.DigitalTimer                                                                                         
        android:id="@+id/dt"                                                                                                        
        android:layout_width="wrap_content"                                                                                                                     
        android:layout_height="wrap_content"                                                                                                        
        app:textBgPaddingLR="10px"                                                                                                  
        app:textBgPaddingTB="2px"                                                                                                           
        app:textBgRes="@drawable/digitalclock_selector"                                                                                             
        app:textColor="#7d3fe4"                                                                                                                 
        app:textSize="7sp"                                                                                                          
        app:timeColonPadding="20px" />                                                                                              
