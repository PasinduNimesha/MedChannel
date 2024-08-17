import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:med_channel/screens/patient/NewAppointmentScreen.dart';
import 'dart:convert';
import 'package:med_channel/styles/colors.dart';

class PhysicianDetailScreen extends StatefulWidget {
  final String physicianId;
  final String patientId;

  const PhysicianDetailScreen({
    Key? key,
    required this.physicianId,
    required this.patientId,
  }) : super(key: key);

  @override
  _PhysicianDetailScreenState createState() => _PhysicianDetailScreenState();
}

class _PhysicianDetailScreenState extends State<PhysicianDetailScreen> {
  late Map<String, dynamic> physicianDetails;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchPhysicianDetails(widget.physicianId);
  }

  Future<void> fetchPhysicianDetails(String id) async {
    try {
      final response = await http.get(
        Uri.parse('http://192.168.43.214:8080/api/v1/physician/$id'),
      );

      if (response.statusCode == 200) {
        setState(() {
          physicianDetails = json.decode(response.body);
          isLoading = false;
        });
      } else {
        // Handle errors
        throw Exception('Failed to load physician details');
      }
    } catch (e) {
      print(e);
      // Optionally, handle the error
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: isLoading
            ? Text('Loading...')
            : Text(physicianDetails['first_name'] + ' ' + physicianDetails['last_name']),
        backgroundColor: Color(MyColors.header01),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(
              child: ClipRRect(
                borderRadius: BorderRadius.circular(10),
                child: Image.network(
                  "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png", // Image fetched from the API
                  height: 200,
                  width: 200,
                  fit: BoxFit.cover,
                ),
              ),
            ),
            SizedBox(height: 20),
            Text(
              physicianDetails['first_name'] + ' ' + physicianDetails['last_name'],
              style: TextStyle(
                fontSize: 22,
                fontWeight: FontWeight.bold,
                color: Color(MyColors.header01),
              ),
            ),
            SizedBox(height: 20),
            Text(
              "Specialty: ${physicianDetails['specialty']}",
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w500,
                color: Color(MyColors.grey02),
              ),
            ),
            SizedBox(height: 10),
            Text(
              "Experience: ${physicianDetails['experience']} years",
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w500,
                color: Color(MyColors.grey02),
              ),
            ),
            SizedBox(height: 10),
            Text(
              "Hospital: ${physicianDetails['hospital']}",
              style: TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.w500,
                color: Color(MyColors.grey02),
              ),
            ),
            SizedBox(height: 30),
            Center(
              child: ElevatedButton(
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) => NewAppointmentScreen(patientId: widget.patientId, physicianId: widget.physicianId)));
                },
                style: ElevatedButton.styleFrom(
                  padding: EdgeInsets.symmetric(horizontal: 40, vertical: 15), backgroundColor: Color(MyColors.yellow01),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                ),
                child: Text(
                  "Make an Appointment",
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
