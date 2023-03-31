def new1(){
    //declaring variable and accessing
    print "Hello"
    def xyz = 10

    print "abc = ${abc}"
    print "xyz = ${xyz}"

    print abc

    //conditions
    if ( abc == "some data") {
        print "YES"
    }
        else{
            print "NO"
        }

    //while loop
    x = 10
    y = 0
    while(x>y){
        println "${y}"
        y++
    }

    //for loop
    for (i=0;i<5;i++){
        println(i)
    }

    //for in loop
    def fruits = ["apple","banana","mango"];
    for (i in fruits){
        println(i)
    }

}