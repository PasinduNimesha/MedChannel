import 'package:flutter/material.dart';


class RegisterationScreen extends StatefulWidget {
  const RegisterationScreen({Key? key}) : super(key: key);

  @override
  _RegisterationScreenState createState() => _RegisterationScreenState();
}

class _RegisterationScreenState extends State<RegisterationScreen> {
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController confirmController = TextEditingController();
  final TextEditingController userNameController = TextEditingController();


  //formkey
  final _formKey = GlobalKey<FormState>();


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          child: Card(
            child: Padding(
              padding: const EdgeInsets.all(20),
              child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text(
                      'Enter Your Details To Register An Account',
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    const Divider(),

                    const SizedBox(
                      height: 40,
                    ),
                    TextFormField(
                      validator: (value) {
                        if (value!.isEmpty || value.length < 4) {
                          return 'Please enter a valid name';
                        }
                        return null;
                      },
                      controller: userNameController,
                      decoration: InputDecoration(
                        labelText: 'Name',
                        hintText: 'Name',
                        border: OutlineInputBorder(
                            borderSide: const BorderSide(
                              color: Colors.white,
                            ),
                            borderRadius: BorderRadius.circular(8)),
                      ),
                    ),
                    const SizedBox(height: 30),
                    TextFormField(
                      validator: (value) {
                        if (value!.isEmpty) {
                          return 'Please A Valid Email';
                        }
                        return null;
                      },
                      controller: emailController,
                      decoration: InputDecoration(
                        labelText: 'Email',
                        hintText: 'Email',
                        border: OutlineInputBorder(
                            borderSide: const BorderSide(
                              color: Colors.white,
                            ),
                            borderRadius: BorderRadius.circular(8)),
                      ),
                    ),
                    const SizedBox(height: 30),

                    ///second text field ===Password
                    TextFormField(
                      validator: (value) {
                        if (value!.isEmpty || value.length < 6) {
                          return 'Please enter a valid password';
                        }
                        return null;
                      },
                      controller: passwordController,
                      obscureText: true,
                      decoration: InputDecoration(
                        labelText: 'Password',
                        hintText: 'Password',
                        border: OutlineInputBorder(
                            borderSide: const BorderSide(
                              color: Colors.white,
                            ),
                            borderRadius: BorderRadius.circular(8)),
                      ),
                    ),
                    // confirm password textfield
                    const SizedBox(height: 30),
                    TextFormField(
                      validator: (value) {
                        if (value!.isEmpty || value.length < 6) {
                          return '''Passowrd lenght should be more than 6 characters''';
                        } else if (value != passwordController.text) {
                          return "Password do not match";
                        }
                        return null;
                      },
                      controller: confirmController,
                      obscureText: true,
                      decoration: InputDecoration(
                        labelText: 'Confirm Password',
                        hintText: 'Confirm Password',
                        border: OutlineInputBorder(
                            borderSide: const BorderSide(
                              color: Colors.white,
                            ),
                            borderRadius: BorderRadius.circular(8)),
                      ),
                    ),
                    const SizedBox(height: 30),
                    //form submit button
                    MaterialButton(
                      elevation: 3,
                      height: 45,
                      highlightElevation: 5,
                      onPressed: () {},

                      child: Text(
                        "Register An Account",
                      ),
                    ),
                    //end of material button
                    const SizedBox(
                      height: 30,
                    ),
                    //TODO: sign up here or register here

                    const SizedBox(
                      height: 20,
                    ),
                    //registration page
                    ////
                    InkWell(
                      onTap: () {
                        Navigator.of(context).pushAndRemoveUntil(
                            MaterialPageRoute(
                                builder: (context) => Container()),
                                (route) => false);
                      },
                      child: Text(
                        "Already having an account? Login here",
                        style: TextStyle(
                          decoration: TextDecoration.underline,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}