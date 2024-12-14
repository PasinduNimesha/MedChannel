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
  final TextEditingController remarksController = TextEditingController();

  // Variables to hold selected date and time
  DateTime? selectedDate;
  TimeOfDay? selectedTime;

  // Form key for validation
  final _formKey = GlobalKey<FormState>();

  // Function to send appointment data to the endpoint
  Future<void> _submitAppointment() async {
    if (_formKey.currentState!.validate()) {
      // Ensure date and time are selected
      if (selectedDate == null || selectedTime == null) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Please select a date and time')),
        );
        return;
      }

      // Format the selected date and time
      final formattedDate = "${selectedDate!.year}-${selectedDate!.month.toString().padLeft(2, '0')}-${selectedDate!.day.toString().padLeft(2, '0')}";
      final formattedTime = "${selectedTime!.hour.toString().padLeft(2, '0')}:${selectedTime!.minute.toString().padLeft(2, '0')}";

      // Build the request payload
      final appointmentData = {
        "patient_id": widget.patientId,
        "doc_id": widget.physicianId,
        "appointment_date": formattedDate,
        "appointment_time": formattedTime,
        "remarks": remarksController.text,
        "created_at": DateTime.now().toIso8601String(),
        "updated_at": DateTime.now().toIso8601String(),
      };

      try {
        final response = await http.post(
          Uri.parse('http://192.168.43.214:8081/api/v1/appointment'),
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

  // Function to pick the date
  Future<void> _pickDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != selectedDate) {
      setState(() {
        selectedDate = picked;
      });
    }
  }

  // Function to pick the time
  Future<void> _pickTime(BuildContext context) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: selectedTime ?? TimeOfDay.now(),
    );
    if (picked != null && picked != selectedTime) {
      setState(() {
        selectedTime = picked;
      });
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
              // Date picker
              ListTile(
                title: Text('Appointment Date'),
                subtitle: Text(
                  selectedDate == null
                      ? 'Select Date'
                      : "${selectedDate!.year}-${selectedDate!.month.toString().padLeft(2, '0')}-${selectedDate!.day.toString().padLeft(2, '0')}",
                ),
                onTap: () => _pickDate(context),
              ),
              // Time picker
              ListTile(
                title: Text('Appointment Time'),
                subtitle: Text(
                  selectedTime == null
                      ? 'Select Time'
                      : "${selectedTime!.hour.toString().padLeft(2, '0')}:${selectedTime!.minute.toString().padLeft(2, '0')}",
                ),
                onTap: () => _pickTime(context),
              ),
              SizedBox(height: 20),
              // Remarks input
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
