import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../physician/HomeScreen.dart';

class PatientRegisterationScreen extends StatefulWidget {
  final String id;
  const PatientRegisterationScreen({Key? key, required this.id}) : super(key: key);

  @override
  _PatientRegisterationScreenState createState() => _PatientRegisterationScreenState();
}

class _PatientRegisterationScreenState extends State<PatientRegisterationScreen> {
  final TextEditingController firstNameController = TextEditingController();
  final TextEditingController lastNameController = TextEditingController();
  final TextEditingController addressController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();
  final TextEditingController genderController = TextEditingController();
  final TextEditingController bloodTypeController = TextEditingController();



  //formkey
  final _formKey = GlobalKey<FormState>();

  // This function will be triggered when the "Register An Account" button is pressed.
  Future<void> register() async {
    if (_formKey.currentState!.validate()) {
      // Gather data from the form fields
      final firstName = firstNameController.text;
      final lastName = lastNameController.text;
      final address = addressController.text;
      final phone = phoneController.text;
      final gender = genderController.text;
      final bloodType = bloodTypeController.text;


      // Prepare the JSON data
      final Map<String, dynamic> registrationData = {
        "patient_id": widget.id,
        "first_name": firstName,
        "last_name": lastName,
        "address": address,
        "phone": phone,
        "gender": gender,
        "blood_type": bloodType,
        "created_at": DateTime.now().toIso8601String(),
        "updated_at": DateTime.now().toIso8601String()

      };

      // Endpoint URL
      final url = Uri.parse('http://192.168.43.214:8080/api/v1/patient');

      try {
        // Send the POST request
        final response = await http.post(
          url,
          headers: {"Content-Type": "application/json"},
          body: jsonEncode(registrationData),
        );

        // Check the response status
        if (response.statusCode == 201) {
          // Registration successful
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Registration Successful!')),
          );
          Navigator.push(context, MaterialPageRoute(builder: (context) => HomeScreen()));
        } else {
          // Registration failed
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Registration Failed: ${response.body}')),
          );
        }
      } catch (e) {
        // Handle errors
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('An error occurred: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SingleChildScrollView(
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
                      controller: firstNameController,
                      decoration: InputDecoration(
                        labelText: 'First Name',
                        hintText: 'First Name',
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
                        if (value!.isEmpty || value.length < 4) {
                          return 'Please enter a valid name';
                        }
                        return null;
                      },
                      controller: lastNameController,
                      decoration: InputDecoration(
                        labelText: 'Last Name',
                        hintText: 'Last Name',
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
                        if (value!.isEmpty || value.length < 1) {
                          return 'Please enter a valid address';
                        }
                        return null;
                      },
                      controller: addressController,
                      decoration: InputDecoration(
                        labelText: 'Address',
                        hintText: 'Address',
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
                        if (value!.isEmpty || value.length < 4) {
                          return 'Please enter a valid phone number';
                        }
                        return null;
                      },
                      controller: phoneController,
                      decoration: InputDecoration(
                        labelText: 'Phone Number',
                        hintText: 'Phone Number',
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
                        if (value!.isEmpty || value.length < 3) {
                          return 'Please enter a valid gender';
                        }
                        return null;
                      },
                      controller: genderController,
                      decoration: InputDecoration(
                        labelText: 'Gender',
                        hintText: 'Gender',
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
                        if (value!.isEmpty || value.length < 4) {
                          return 'Please enter a valid blood type';
                        }
                        return null;
                      },
                      controller: bloodTypeController,
                      decoration: InputDecoration(
                        labelText: 'Blood Type',
                        hintText: 'Blood Type',
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
                      onPressed: () {
                        register();
                      },
                      child: Text(
                        "Register An Account",
                      ),
                    ),
                    //end of material button
                    const SizedBox(
                      height: 30,
                    ),
                    const SizedBox(
                      height: 20,
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
