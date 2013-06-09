/*
*   #########  代码的详细内容，请参考www.javabloger.com   #############
*/


namespace java com.javabloger.gen.code   #  注释1

struct Blog {   #  注释2
	1: string topic
	2: binary content
	3: i64    createdTime
	4: string id
	5: string ipAddress
	6: map<string,string> props
  }


service ThriftCase {  #  注释3
    i32 testCase1(1:i32 num1, 2:i32 num2, 3:string  num3)  #  注释4

    list<string> testCase2(1:map<string,string>  num1)

    void testCase3()

   void testCase4(1:list<Blog> blog)
    
}


