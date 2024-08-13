import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class RegisterationScreen extends StatefulWidget {
  final String id;
  const RegisterationScreen({Key? key, required this.id}) : super(key: key);

  @override
  _RegisterationScreenState createState() => _RegisterationScreenState();
}

class _RegisterationScreenState extends State<RegisterationScreen> {
  final TextEditingController firstNameController = TextEditingController();
  final TextEditingController lastNameController = TextEditingController();
  final TextEditingController experienceController = TextEditingController();
  final TextEditingController specialtyController = TextEditingController();
  final TextEditingController hospitalController = TextEditingController();
  final TextEditingController licenseController = TextEditingController();

  //formkey
  final _formKey = GlobalKey<FormState>();

  // This function will be triggered when the "Register An Account" button is pressed.
  Future<void> register() async {
    if (_formKey.currentState!.validate()) {
      // Gather data from the form fields
      final firstName = firstNameController.text;
      final lastName = lastNameController.text;
      final experience = int.parse(experienceController.text);
      final specialty = specialtyController.text;
      final hospital = hospitalController.text;
      final licenseNo = licenseController.text;

      // Prepare the JSON data
      final Map<String, dynamic> registrationData = {
        "id": widget.id,
        "first_name": firstName,
        "last_name": lastName,
        "available": true,
        "experience": experience,
        "specialty": specialty,
        "hospital": hospital,
        "license_no": licenseNo,
        "created_at": DateTime.now().toIso8601String(),
        "updated_at": DateTime.now().toIso8601String()
      };

      // Endpoint URL
      final url = Uri.parse('http://192.168.43.214:8080/api/v1/doctor');

      try {
        // Send the POST request
        final response = await http.post(
          url,
          headers: {"Content-Type": "application/json"},
          body: jsonEncode(registrationData),
        );

        // Check the response status
        if (response.statusCode == 200) {
          // Registration successful
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Registration Successful!')),
          );
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
                          return 'Please enter a valid number';
                        }
                        return null;
                      },
                      controller: experienceController,
                      decoration: InputDecoration(
                        labelText: 'Experience Years',
                        hintText: 'Experience Years',
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
                          return 'Please enter a valid specialty';
                        }
                        return null;
                      },
                      controller: specialtyController,
                      decoration: InputDecoration(
                        labelText: 'Specialty',
                        hintText: 'Specialty',
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
                          return 'Please enter a valid hospital';
                        }
                        return null;
                      },
                      controller: hospitalController,
                      decoration: InputDecoration(
                        labelText: 'Hospital',
                        hintText: 'Hospital',
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
                          return 'Please enter a valid license';
                        }
                        return null;
                      },
                      controller: licenseController,
                      decoration: InputDecoration(
                        labelText: 'License',
                        hintText: 'License',
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
