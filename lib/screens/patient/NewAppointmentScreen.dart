import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class NewAppointmentScreen extends StatefulWidget {
  final String patientId;
  final String physicianId;
  const NewAppointmentScreen({super.key, required this.patientId, required this.physicianId});

  @override
  State<NewAppointmentScreen> createState() => _NewAppointmentScreenState();
}

class _NewAppointmentScreenState extends State<NewAppointmentScreen> {
  // Text controllers for form fields
  final TextEditingController appointmentDateController = TextEditingController();
  final TextEditingController appointmentTimeController = TextEditingController();
  final TextEditingController remarksController = TextEditingController();

  // Form key for validation
  final _formKey = GlobalKey<FormState>();

  // Function to send appointment data to the endpoint
  Future<void> _submitAppointment() async {
    if (_formKey.currentState!.validate()) {
      // Build the request payload
      final appointmentData = {
        "patient_id": widget.patientId,
        "doc_id": widget.physicianId,
        "appointment_date": appointmentDateController.text,
        "appointment_time": appointmentTimeController.text,
        "remarks": remarksController.text,
        "created_at": DateTime.now().toIso8601String(),
        "updated_at": DateTime.now().toIso8601String(),
      };

      try {
        // Send a POST request to the API endpoint
        final response = await http.post(
          Uri.parse('http://192.168.43.214:8080/api/v1/appointment'),
          headers: {"Content-Type": "application/json"},
          body: json.encode(appointmentData),
        );

        if (response.statusCode == 201) {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: Text('Appointment Created'),
                content: Text('Your appointment has been successfully created.'),
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                      Navigator.of(context).pop();
                      Navigator.of(context).pop();
                    },
                    child: Text('OK'),
                  ),
                ],
              );
            },
          );

        } else {
          // If the server returns an error, show an error message
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Failed to create appointment.')),
          );
        }
      } catch (error) {
        // Handle any exceptions
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('An error occurred: $error')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('New Appointment'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                controller: appointmentDateController,
                decoration: InputDecoration(labelText: 'Appointment Date (YYYY-MM-DD)'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the appointment date';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: appointmentTimeController,
                decoration: InputDecoration(labelText: 'Appointment Time (HH:MM)'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the appointment time';
                  }
                  return null;
                },
              ),
              SizedBox(height: 20),
              //text area for remarks
              TextFormField(
                controller: remarksController,
                decoration: InputDecoration(labelText: 'Remarks'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the remarks';
                  }
                  return null;
                },
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: _submitAppointment,
                child: Text('Make Appointment'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
